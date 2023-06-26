package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListPersons;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
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

    /**
     * Read - Get all persons
     * 
     * @return - A list of persons full filled
     * @throws IOException
     */
    @GetMapping("/persons")
    public MappingJacksonValue getPersons() throws IOException {
	ListPersons listPersons = personService.getPersons();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listPersons);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all persons that are deserved at their address by a station
     * 
     * @param stationNumber number of a firestaion
     * @return - A List of Persons that are deserved by a certain firestation
     * @throws IOException
     */
    @GetMapping("/firestation")
    public MappingJacksonValue getPersonsCoveredByFireStation(@RequestParam int stationNumber) throws IOException {
	// List of Object to be able to add information to JSON
	List<Object> listToSend = new ArrayList<Object>();
	// List of person to browse the filtered list
	List<Person> listPersonsSorted = new ArrayList<Person>();
	// Get the full list of persons
	List<Person> listPersonsCompare = personService.getPersons().getListPersons();
	// Get the full list of firestations
	List<FireStation> listFireStationByNumber = fireStationService.getFirestationsByStationNumber(stationNumber)
		.getListFirestation();
	// Get the full list of medicalRecords
	List<MedicalRecords> listMedicalRecords = medicalRecordService.getMedicalRecords().getListMedicalrecords();
	// HashMap to be able to add key/value information like number of child (under
	// 18)
	HashMap<String, Integer> hm = new HashMap<String, Integer>();
	int personnesMajeurs = 0;
	int personnesMineurs = 0;
	// Date to calculate age of persons
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	// Browse persons list and firestations list to compare the address of the two
	// lists
	// to add what we want to get
	for (FireStation fs : listFireStationByNumber) {
	    for (Person p : listPersonsCompare) {
		if (p.getAddress().equals(fs.getAddress())) {
		    listToSend.add(p);
		    listPersonsSorted.add(p);
		}
	    }
	}
	// Browse persons list and medical records list to get birthdate and calculate
	// age
	for (Person p : listPersonsSorted) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (p.getFirstName().equals(mr.getFirstName()) && p.getLastName().equals(mr.getLastName())) {
		    bd = mr.getBirthdate();
		    ageDiff = ChronoUnit.DAYS.between(bd, ld);
		    // 18 years is equal to 6570 days
		    if (ageDiff <= 6570) {
			personnesMineurs++;
		    } else {
			personnesMajeurs++;
		    }
		}
	    }
	}
	hm.put("Personne(s) majeur(s)", personnesMajeurs);
	hm.put("Personne(s) mineur(s)", personnesMineurs);
	listToSend.add(hm);
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName",
		"address", "phone");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all child (under 18) who live at a certain address
     * 
     * @param address address where lives child or live children
     * @return - A list of child with the other members of a house or an empty list
     *         if no child
     * @throws IOException
     */
    @GetMapping("/childAlert")
    public MappingJacksonValue getChildByAddress(@RequestParam String address) throws IOException {
	List<Object> listToSend = new ArrayList<Object>();
	List<Person> listOtherPersonsInHouse = new ArrayList<Person>();
	List<Person> listPersonsByAddress = personService.getPersonsByAdress(address).getListPersons();
	List<MedicalRecords> listMedicalRecords = medicalRecordService.getMedicalRecords().getListMedicalrecords();
	HashMap<String, Long> childAge = new HashMap<String, Long>();
	HashMap<String, List<Person>> otherPeopleInHouse = new HashMap<String, List<Person>>();
	// Date to calculate age of persons
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiffDays = 0;
	long ageDiffYears = 0;
	for (Person p : listPersonsByAddress) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (mr.getFirstName().equals(p.getFirstName())) {
		    bd = mr.getBirthdate();
		    ageDiffDays = ChronoUnit.DAYS.between(bd, ld);
		    ageDiffYears = ChronoUnit.YEARS.between(bd, ld);
		    if (ageDiffDays <= 6570) {
			childAge.put("Âge du mineur " + p.getFirstName(), ageDiffYears);
			listToSend.add(p);
		    } else {
			listOtherPersonsInHouse.add(p);
		    }
		}

	    }
	}

	if (listOtherPersonsInHouse != null) {
	    otherPeopleInHouse.put("Autre(s) membre(s) du foyer ", listOtherPersonsInHouse);
	}
	listToSend.add(childAge);
	listToSend.add(otherPeopleInHouse);
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all persons
     * 
     * @return - A list of persons full filled
     * @throws IOException
     */
    @GetMapping("/phoneAlert")
    public MappingJacksonValue getPhoneNumberDeservedByFirestations(@RequestParam int firestation) throws IOException {
	List<Person> listPersonsCompare = personService.getPersons().getListPersons();
	List<Person> listPhoneNumber = new ArrayList<Person>();
	Set<String> setPhoneNumber = new HashSet<String>();
	List<FireStation> listFireStationByNumber = fireStationService.getFirestationsByStationNumber(firestation)
		.getListFirestation();
	for (FireStation fs : listFireStationByNumber) {
	    for (Person p : listPersonsCompare) {
		if (fs.getAddress().equals(p.getAddress())) {
		    listPhoneNumber.add(p);
		}
	    }
	}
	for (Person p : listPhoneNumber) {
	    System.out.println(p.getFirstName());
	    setPhoneNumber.add(p.getPhone());
	}
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("phone");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(setPhoneNumber);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }
}
