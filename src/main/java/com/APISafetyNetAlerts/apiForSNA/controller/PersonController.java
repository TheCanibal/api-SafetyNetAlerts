package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.FirestationPersonService;
import com.APISafetyNetAlerts.apiForSNA.service.MedicalRecordService;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private FirestationPersonService firestationPersonService;

    /**
     * Read - Get all child (under 18) who live at an address
     * 
     * @param address address where live child or children
     * @return - A list of child with the other members of a house or an empty list
     *         if no child
     * @throws IOException
     */
    @GetMapping("/childAlert")
    public MappingJacksonValue getChildByAddress(@RequestParam String address) throws IOException {
	// a list of persons to send
	List<PersonAdaptative> listToSend = new ArrayList<PersonAdaptative>();

	// a list of persons who live at a certain address
	List<PersonAdaptative> listPersonsByAddress = personService.getPersonsAdaptativeByAdress(address)
		.getListPersons();

	// a list of minors persons (can be empty)
	List<PersonAdaptative> minorsList = medicalRecordService.getListOfMinorsPersons(listPersonsByAddress);

	// a list of majors persons (can be empty)
	List<PersonAdaptative> majorsList = medicalRecordService.getListOfMajorsPersons(listPersonsByAddress);

	// for each minors, add to the list
	listToSend.addAll(minorsList);

	// if there are minors, add other member of the house, else the list stay empty
	if (!listToSend.isEmpty()) {
	    listToSend.addAll(majorsList);
	}

	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName",
		"age");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all phone numbers of persons who are deserved by a firestation
     * 
     * @param firestation Firestation number
     * @return - A list of phone number
     * @throws IOException
     */
    @GetMapping("/phoneAlert")
    public MappingJacksonValue getPhoneNumberDeservedByFirestations(@RequestParam int firestation) throws IOException {

	// list of persons covered by a firestation
	List<Person> listPersonsCoveredByStation = firestationPersonService.getPersonsAtFireStationAddress(firestation);

	// list of phone number without duplicates
	Set<String> setPhoneNumber = new HashSet<String>();

	// for each person in the list, add the phone number in Set to remove duplicates
	for (Person p : listPersonsCoveredByStation) {
	    setPhoneNumber.add(p.getPhone());
	}
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("phone");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(setPhoneNumber);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all persons info with first name and last name
     *
     * 
     * @param firstName first name of person
     * @param lastName  last name of person
     * @return - A list of persons
     * @throws IOException
     */
    @GetMapping("/personInfo")
    public MappingJacksonValue getPersonInfoByFirstNameAndLastName(@RequestParam String firstName,
	    @RequestParam String lastName) throws IOException {

	// list of persons covered by a firestation
	List<PersonAdaptative> listPersonsByLastName = personService.getPersonsAdaptativeByLastName(lastName)
		.getListPersons();

	List<PersonAdaptative> listToSend = new ArrayList<PersonAdaptative>();

	for (PersonAdaptative p : listPersonsByLastName) {
	    if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
		listToSend.add(p);
	    } else if (p.getLastName().equals(lastName) && !listToSend.contains(p)) {
		listToSend.add(p);
	    }
	}

	listToSend = medicalRecordService.getListOfPersonsWithAge(listToSend);
	listToSend = medicalRecordService.getListPersonsWithTheirMedicalBackgrounds(listToSend);

	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "address", "age",
		"mail", "medications", "allergies");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all email by city
     * 
     * 
     * @param city city
     * @return - A list of email
     * @throws IOException
     */
    @GetMapping("/communityEmail")
    public MappingJacksonValue getPersonsMailByCity(@RequestParam String city) throws IOException {

	List<Person> listToSend = new ArrayList<Person>();
	List<Person> listPersonsByCity = personService.getPersonsByCity(city).getListPersons();

	for (Person p : listPersonsByCity) {
	    if (p.getCity().equals(city)) {
		listToSend.add(p);
	    }
	}

	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("email");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

}
