package com.APISafetyNetAlerts.apiForSNA.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;
import com.APISafetyNetAlerts.apiForSNA.util.FirestationPersonUtil;
import com.APISafetyNetAlerts.apiForSNA.util.FirestationUtil;
import com.APISafetyNetAlerts.apiForSNA.util.MedicalRecordUtil;
import com.APISafetyNetAlerts.apiForSNA.util.PersonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FireStationController {

    @Autowired
    private FirestationUtil fireStationUtil;

    @Autowired
    private MedicalRecordUtil medicalRecordUtil;

    @Autowired
    private FirestationPersonUtil firestationPersonUtil;

    @Autowired
    private PersonUtil personUtil;

    @Autowired
    private FireStationService firestationService;

    @Autowired
    private PersonService personService;

    private static Logger logger = LogManager.getLogger(FireStationController.class);

    /**
     * Read - Get all Firestations
     * 
     * @return all firestations
     */
    @GetMapping("/firestations")
    public ListFireStations getAllFirestations() {
	return firestationService.getAllFireStations();
    }

    /**
     * Read - Get all persons that are covered by a station
     * 
     * @param stationNumber firestation number
     * @return - A List of Persons that are covered by firestation and the number of
     *         minors and majors persons
     */
    @GetMapping("/firestation")
    public MappingJacksonValue getPersonsCoveredByFireStation(@RequestParam int stationNumber) {

	// List to return
	ListPersonAdaptative listToSend = new ListPersonAdaptative();
	// List of station numbers
	List<Integer> stationNumbers = fireStationUtil.getAllStationNumber();
	// Filter rules to apply to the bean
	SimpleBeanPropertyFilter monFiltre;
	// This allow the filter to apply to all the beans with dynamic filters
	FilterProvider filtres;
	// To be able to set the filters concretely
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	try {
	    if (stationNumbers.contains(stationNumber)) {

		// List of person to browse the filtered list
		List<PersonAdaptative> listPersonsSorted = firestationPersonUtil
			.getPersonsAdaptativeByFireStationNumber(stationNumber);
		// Set the list Person in ListPerson class
		listToSend.setListPersons(listPersonsSorted);

		// get number of majors
		int personnesMajeures = medicalRecordUtil.getNumberOfMajorsPersons(listPersonsSorted);
		// get number of minors
		int personnesMineures = medicalRecordUtil.getNumberOfMinorsPersons(listPersonsSorted);
		logger.info("Il y a {} personnes mineurs et {} personnes majeures dans la liste", personnesMineures,
			personnesMajeures);
		logger.info("Nombre de personnes dans la liste : {}", listToSend.getListPersons().size());
		// set the amount of minors
		listToSend.setPersonnesMineures(personnesMineures);
		// set the amount of majors
		listToSend.setPersonnesMajeures(personnesMajeures);

		// Filters
		monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName", "address", "phone",
			"personnesMajeures", "personnesMineures");
		filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
		personFiltres = new MappingJacksonValue(listToSend);
		personFiltres.setFilters(filtres);
		// Log the result if debug mode is enabled
		if (logger.isDebugEnabled()) {
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
			String json = objectMapper.setFilterProvider(filtres).writerWithDefaultPrettyPrinter()
				.writeValueAsString(listToSend);
			logger.debug("La liste de personnes qui est renvoyée : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}
		return personFiltres;
	    } else {
		throw new IllegalArgumentException("La station n'existe pas !");
	    }
	} catch (IllegalArgumentException iae) {
	    logger.error("La station numéro {} n'existe pas !", stationNumber);
	    return personFiltres;
	}

    }

    /**
     * Read - Get all persons who live at an address and the firestation number that
     * deserved the address
     * 
     * @param address address of a person
     * 
     * @return - A List of Persons who live at the address and the firestation
     *         number that deserved the address
     * 
     */

    @GetMapping("/fire")
    public MappingJacksonValue getPersonsLiveAtAddressDeservedByStation(@RequestParam String address) {
	// List to return
	List<PersonAdaptative> listToSend = new ArrayList<PersonAdaptative>();
	// List to fill first before fill the list to return in case of wrong argument
	List<PersonAdaptative> listPersonsAdaptative = new ArrayList<PersonAdaptative>();
	// List to compare the argument to the list of addresses
	List<String> allAddresses = personUtil.getAddressFromListPersons();
	logger.debug("Liste de toutes les addresses : {}", allAddresses.toString());
	// Filter rules to apply to the bean
	SimpleBeanPropertyFilter monFiltre;
	// This allow the filter to apply to all the beans with dynamic filters
	FilterProvider filtres;
	// To be able to set the filters concretely
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	try {
	    if (address != "" && allAddresses.contains(address)) {
		// Fill the list with a list of person who live at the address
		listPersonsAdaptative = personService.getPersonsAdaptativeByAdress(address).getListPersons();
		logger.info("L'adresse est {}", address);
		// Add the age of all persons in the list
		listToSend = medicalRecordUtil.getListOfPersonsWithAge(listPersonsAdaptative);
		// Add the medical backgrounds to the list
		listToSend = medicalRecordUtil.getListPersonsWithTheirMedicalBackgrounds(listToSend);
		// Add the firestations number to the list
		listToSend = fireStationUtil.getStationNumberByListPersons(listToSend);
		logger.info("Nombre de personnes dans la liste : {}", listToSend.size());
		monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "phone", "firestationNumber", "age",
			"medications", "allergies");
		filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
		personFiltres = new MappingJacksonValue(listToSend);
		personFiltres.setFilters(filtres);
		// Log the result if debug mode is enabled
		if (logger.isDebugEnabled()) {
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
			String json = objectMapper.setFilterProvider(filtres).writerWithDefaultPrettyPrinter()
				.writeValueAsString(listToSend);
			logger.debug("La liste de personnes qui est renvoyée : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}
		return personFiltres;
	    } else {
		throw new IllegalArgumentException("L'addresse est erronée !");
	    }
	} catch (IllegalArgumentException e) {
	    logger.error("L'addresse {} ne fait pas partie de la liste d'adresses !", address);
	    return personFiltres;
	}

    }

    /**
     * Read - Get all persons that are deserved by a list of firestation
     * 
     * @param stations array of stations
     * 
     * @return - A List of Persons that are deserved by firestations
     * 
     */

    @GetMapping("/flood/stations")
    public MappingJacksonValue getAllPersonsCoveredByFirestations(@RequestParam int[] stations) {
	// List to return
	List<PersonAdaptative> listToSend = new ArrayList<PersonAdaptative>();
	// List to fill first before fill the list to return in case of wrong argument
	List<PersonAdaptative> listPersonsByFireStation = new ArrayList<PersonAdaptative>();
	// list of all station number
	List<Integer> listStationsNumbers = fireStationUtil.getAllStationNumber();
	logger.debug("Contenu de la liste des numéros de stations : {}", listStationsNumbers.toString());
	// change array param into a list
	List<Integer> listStationsNumbersParam = Arrays.stream(stations).boxed().collect(Collectors.toList());
	logger.debug("Contenu du paramètre \"tableau de numéros de stations\" : {}",
		listStationsNumbersParam.toString());
	// List of station to compare
	List<Integer> listStationsNumbersCompare = fireStationUtil
		.compareElementsOfTwoListAndSendListWithSameElements(listStationsNumbersParam, listStationsNumbers);
	logger.debug("Contenu de la liste qui enlève les doublons et les mauvais numéros de stations : {}",
		listStationsNumbersCompare.toString());
	// Filter rules to apply to the bean
	SimpleBeanPropertyFilter monFiltre;
	// This allow the filter to apply to all the beans with dynamic filters
	FilterProvider filtres;
	// To be able to set the filters concretely
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	try {
	    if (!listStationsNumbersCompare.isEmpty()) {
		listPersonsByFireStation = firestationPersonUtil.getPersonsByListFireStations(stations);
		// Add the age of all persons in the list
		listToSend = medicalRecordUtil.getListOfPersonsWithAge(listPersonsByFireStation);
		// Add the medical backgrounds of all persons in the list
		listToSend = medicalRecordUtil.getListPersonsWithTheirMedicalBackgrounds(listToSend);
		// Add the firestation number of all persons in the list
		listToSend = fireStationUtil.getStationNumberByListPersons(listToSend);
		logger.info("Nombre de personnes dans la liste : {}", listToSend.size());
		monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "phone", "firestationNumber", "age",
			"medications", "allergies");
		filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
		personFiltres = new MappingJacksonValue(listToSend);
		personFiltres.setFilters(filtres);
		// Log the result if debug mode is enabled
		if (logger.isDebugEnabled()) {
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
			String json = objectMapper.setFilterProvider(filtres).writerWithDefaultPrettyPrinter()
				.writeValueAsString(listToSend);
			logger.debug("La liste de personnes qui est renvoyée : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}
		return personFiltres;
	    } else {
		throw new IllegalArgumentException("La liste de numéros de stations est erronée");
	    }

	} catch (IllegalArgumentException e) {
	    logger.error("La liste contient des numéros de stations qui sont incorrectes");
	    return personFiltres;
	}
    }

    /**
     * Create - Add new firestation to the JSON File
     * 
     * @param firestation firestation to add
     * @return created firestation
     */
    @PostMapping("/firestation")
    public FireStation createFirestation(@RequestBody FireStation firestation) {
	return firestationService.createFirestation(firestation);
    }

    /**
     * Update - station number of a fireStation
     * 
     * @param fireStation fireStation to update
     */
    @PutMapping("/firestation")
    public void updateFirestation(@RequestBody FireStation fireStation) {
	firestationService.updateFirestation(fireStation);
    }
}
