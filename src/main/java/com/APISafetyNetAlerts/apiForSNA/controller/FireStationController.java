package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
import com.APISafetyNetAlerts.apiForSNA.service.MedicalRecordService;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Read - Get all firestations
     * 
     * @return - A List of firestations full filled
     * @throws IOException
     */
    @GetMapping("/firestations")
    public MappingJacksonValue getFireStations() throws IOException {
	ListFireStations listFireStations = fireStationService.getFireStations();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamique", monFiltre);
	MappingJacksonValue fireStationsFiltres = new MappingJacksonValue(listFireStations);
	fireStationsFiltres.setFilters(filtres);
	return fireStationsFiltres;
    }

    /**
     * Read - Get all persons that are deserved at their address by a station
     * 
     * @param stationNumber number of a firestaion
     * @return - A List of Persons that are deserved by a certain firestation and
     *         the number of minors and majors persons
     * @throws IOException
     */
    @GetMapping("/firestation")
    public MappingJacksonValue getPersonsCoveredByFireStation(@RequestParam int stationNumber) throws IOException {
	// List of Object to be able to add information to JSON
	List<Object> listToSend = new ArrayList<Object>();
	// List of person to browse the filtered list
	List<Person> listPersonsSorted = new ArrayList<Person>();
	// Get the full list of persons
	List<Person> listAllPersons = personService.getPersons().getListPersons();
	// Get the full list of firestations
	List<FireStation> listFireStationByNumber = fireStationService.getFirestationsByStationNumber(stationNumber)
		.getListFirestation();

	// HashMap to be able to add key/value information like number of child (under
	// 18)
	HashMap<String, Integer> hm = new HashMap<String, Integer>();

	listPersonsSorted.addAll(listAllPersons.stream()
		.filter(e -> listFireStationByNumber.stream().anyMatch(s -> e.getAddress().equals(s.getAddress())))
		.collect(Collectors.toList()));
	listToSend.addAll(listPersonsSorted);

	int personnesMajeures = medicalRecordService.getNumberOfMajorsPersons(listPersonsSorted);
	int personnesMineures = medicalRecordService.getNumberOfMinorsPersons(listPersonsSorted);
	hm.put("PersonnesMajeurs", personnesMajeures);
	hm.put("PersonneMineurs", personnesMineures);
	listToSend.add(hm);
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName",
		"address", "phone");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

}
