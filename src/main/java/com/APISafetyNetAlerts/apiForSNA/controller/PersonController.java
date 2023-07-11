package com.APISafetyNetAlerts.apiForSNA.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;
import com.APISafetyNetAlerts.apiForSNA.util.FirestationPersonUtil;
import com.APISafetyNetAlerts.apiForSNA.util.MedicalRecordUtil;
import com.APISafetyNetAlerts.apiForSNA.util.PersonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordUtil medicalRecordUtil;

    @Autowired
    private FirestationPersonUtil firestationPersonUtil;

    @Autowired
    private PersonUtil personUtil;

    private static Logger logger = LogManager.getLogger(PersonController.class);

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
	List<Person> listAllPersons = personService.getAllPersons().getListPersons();
	return listAllPersons;
    }

    /**
     * Read - Get all child (under 18) who live at an address
     * 
     * @param address address where live child or children
     * @return - A list of child with the other members of a house or an empty list
     *         if no child
     */
    @GetMapping("/childAlert")
    public MappingJacksonValue getChildByAddress(@RequestParam String address) {

	// a list of persons to send
	List<PersonAdaptative> listToSend = new ArrayList<PersonAdaptative>();

	// a list of persons who live at a certain address
	List<PersonAdaptative> listPersonsByAddress = new ArrayList<PersonAdaptative>();

	// a list of minors persons (can be empty)
	List<PersonAdaptative> minorsList = medicalRecordUtil.getListOfMinorsPersons(listPersonsByAddress);

	// a list of majors persons (can be empty)
	List<PersonAdaptative> majorsList = medicalRecordUtil.getListOfMajorsPersons(listPersonsByAddress);

	// list of all addresses in file
	List<String> allAddresses = personUtil.getAddressFromListPersons();

	// Filter rules to apply to the bean
	SimpleBeanPropertyFilter monFiltre;
	// This allow the filter to apply to all the beans with dynamic filters
	FilterProvider filtres;
	// To be able to set the filters concretely
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	try {
	    if (address != "" && allAddresses.contains(address)) {
		listPersonsByAddress = personService.getPersonsAdaptativeByAdress(address).getListPersons();
		logger.info("L'adresse est {}", address);
		// for each minors, add to the list
		listToSend.addAll(minorsList);

		// if there are minors, add other member of the house, else the list stay empty
		if (!listToSend.isEmpty()) {
		    listToSend.addAll(majorsList);
		    logger.info("Nombre de personnes dans la liste : {}", listToSend.size());
		}

		monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName", "age");
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
     * Read - Get all phone numbers of persons who are deserved by a firestation
     * 
     * @param firestation Firestation number
     * @return - A list of phone number
     */
    @GetMapping("/phoneAlert")
    public MappingJacksonValue getPhoneNumberDeservedByFirestations(@RequestParam int firestation) {

	// list of persons covered by a firestation
	List<Person> listPersonsCoveredByStation = new ArrayList<Person>();

	// list of phone number without duplicates
	Set<String> setPhoneNumber = new HashSet<String>();

	// Filter rules to apply to the bean
	SimpleBeanPropertyFilter monFiltre;
	// This allow the filter to apply to all the beans with dynamic filters
	FilterProvider filtres;
	// To be able to set the filters concretely
	MappingJacksonValue personFiltres = new MappingJacksonValue(setPhoneNumber);

	try {
	    if (firestation > 0) {
		listPersonsCoveredByStation = firestationPersonUtil.getPersonsByFireStationNumber(firestation);
		logger.info("Le numéro de station est {}", firestation);
		// for each person in the list, add the phone number in Set to remove duplicates
		for (Person p : listPersonsCoveredByStation) {
		    setPhoneNumber.add(p.getPhone());
		}
		logger.info("Nombre de personnes dans la liste : {}", setPhoneNumber.size());
		monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("phone");
		filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
		personFiltres = new MappingJacksonValue(setPhoneNumber);
		personFiltres.setFilters(filtres);
		// Log the result if debug mode is enabled
		if (logger.isDebugEnabled()) {
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
			String json = objectMapper.setFilterProvider(filtres).writerWithDefaultPrettyPrinter()
				.writeValueAsString(setPhoneNumber);
			logger.debug("La liste de numéro de téléphone qui est renvoyée : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}
		return personFiltres;
	    } else {
		throw new IllegalArgumentException("Numéro de station erroné : doit être supérieur à 0 !");
	    }
	} catch (IllegalArgumentException e) {
	    logger.error("Le numéro de station est erroné ! Il est égal à {}", firestation);
	    return personFiltres;
	}
    }

    /**
     * Read - Get all persons info with first name and last name
     *
     * 
     * @param firstName first name of person
     * @param lastName  last name of person
     * @return - A list of persons
     */
    @GetMapping("/personInfo")
    public MappingJacksonValue getPersonInfoByFirstNameAndLastName(@RequestParam String firstName,
	    @RequestParam String lastName) {

	// list of persons by last name
	List<PersonAdaptative> listPersonsByLastName = new ArrayList<PersonAdaptative>();

	// List to return
	List<PersonAdaptative> listToSend = new ArrayList<PersonAdaptative>();

	// List of all first name to compare with argument
	List<String> allPersonsFirstName = personUtil.getFirstNameFromListPersons();

	// List of all last name to compare with argument
	List<String> allPersonsLastName = personUtil.getLastNameFromListPersons();

	// Filter rules to apply to the bean
	SimpleBeanPropertyFilter monFiltre;
	// This allow the filter to apply to all the beans with dynamic filters
	FilterProvider filtres;
	// To be able to set the filters concretely
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	try {
	    if (allPersonsFirstName.contains(firstName) || allPersonsLastName.contains(lastName)) {
		listPersonsByLastName = personService.getPersonsAdaptativeByLastName(lastName).getListPersons();

		// For each person, add to the list persons with the first name or the last name
		// argument
		for (PersonAdaptative p : listPersonsByLastName) {
		    if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
			listToSend.add(p);
		    } else if (p.getLastName().equals(lastName) && !listToSend.contains(p)) {
			listToSend.add(p);
		    }
		}

		// Add the age of all persons in the list
		listToSend = medicalRecordUtil.getListOfPersonsWithAge(listToSend);
		// Add the medical backgrounds of all persons in the list
		listToSend = medicalRecordUtil.getListPersonsWithTheirMedicalBackgrounds(listToSend);
		logger.info("Nombre de personnes dans la liste : {}", listToSend.size());
		monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("lastName", "address", "age", "mail",
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
			logger.debug("La liste de numéro de téléphone qui est renvoyée : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}
		return personFiltres;
	    } else {
		throw new IllegalArgumentException("Le prénom ou le nom ne fait pas partie de la liste");
	    }
	} catch (IllegalArgumentException e) {
	    logger.error("Le nom {} ou le prénom {} est erroné", lastName, firstName);
	    return personFiltres;
	}
    }

    /**
     * Read - Get all email by city
     * 
     * 
     * @param city city
     * @return - A list of email
     */
    @GetMapping("/communityEmail")
    public MappingJacksonValue getPersonsMailByCity(@RequestParam String city) {

	// list to return
	List<Person> listToSend = new ArrayList<Person>();
	// list of persons from city
	List<Person> listPersonsByCity = new ArrayList<Person>();
	// List of all cities
	List<String> listCities = personUtil.getCityFromListPersons();
	// Filter rules to apply to the bean
	SimpleBeanPropertyFilter monFiltre;
	// This allow the filter to apply to all the beans with dynamic filters
	FilterProvider filtres;
	// To be able to set the filters concretely
	MappingJacksonValue personFiltres = new MappingJacksonValue(listToSend);
	try {
	    if (listCities.contains(city)) {
		listPersonsByCity = personService.getPersonsByCity(city).getListPersons();
		// for each person from city add it to the list
		for (Person p : listPersonsByCity) {
		    if (p.getCity().equals(city)) {
			listToSend.add(p);
		    }
		}

		monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("email");
		filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
		personFiltres = new MappingJacksonValue(listToSend);
		personFiltres.setFilters(filtres);
		// Log the result if debug mode is enabled
		if (logger.isDebugEnabled()) {
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
			String json = objectMapper.setFilterProvider(filtres).writerWithDefaultPrettyPrinter()
				.writeValueAsString(listToSend);
			logger.debug("La liste de mail qui est renvoyée : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}
		return personFiltres;
	    } else {
		throw new IllegalArgumentException("La ville est erronée !");
	    }
	} catch (IllegalArgumentException e) {
	    logger.error("La ville {} ne fait pas partie de la liste !", city);
	    return personFiltres;

	}
    }

    /**
     * Add new person to the JSON File
     * 
     * @param person person to add
     * @return created person
     */
    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {

	return personService.createPerson(person);
    }

}
