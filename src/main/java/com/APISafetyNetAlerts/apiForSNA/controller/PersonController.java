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

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
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
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private FirestationPersonService firestationPersonService;

    /**
     * Read - Get all persons
     * 
     * @return - A list of persons full filled
     * @throws IOException
     */
    @GetMapping("/persons")
    public MappingJacksonValue getPersons() throws IOException {
	ListPerson listPersons = personService.getPersons();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listPersons);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all child (under 18) who live at a certain address
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
	for (PersonAdaptative p : minorsList) {
	    listToSend.add(p);
	}
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
     * Read - Get all phone numbers of persons who are deserved by a certain
     * firestation
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
}
