package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Interaction with the object persons in the data.json file
 *
 */

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    // ObjectMapper to be able to deserialize JSON
    private ObjectMapper mapper = new ObjectMapper();
    // List persons to load only one time
    private ListPerson loadListPersons;
    // List person to send
    private ListPerson listPersonsToSend = new ListPerson();
    // List persons adaptative to load only one time
    private ListPersonAdaptative loadListPersonsAdaptative;
    // List persons adaptative to send
    private ListPersonAdaptative listPersonsAdaptativeToSend = new ListPersonAdaptative();
    // List person to sort and to set
    private List<Person> listPersonsSorted;
    // List persons adaptative to sort and to set
    private List<PersonAdaptative> listPersonsAdaptativeSorted;
    // Logger
    private static Logger logger = LogManager.getLogger(PersonRepositoryImpl.class);

    /**
     * Read JSON file if it has not been read already and load it
     * 
     * @return list of persons from file
     */
    public ListPerson loadPersons() {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	if (loadListPersons == null) {
	    try {
		File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
		loadListPersons = mapper.readValue(file, ListPerson.class);
		logger.info("Le fichier est lu !");
		return loadListPersons;
	    } catch (IOException e) {
		logger.error("Fichier introuvable");
	    }
	}
	return loadListPersons;
    }

    /**
     * Read JSON file if it has not been read already
     * 
     * @return list of person adaptative from file
     */
    public ListPersonAdaptative loadPersonsAdaptative() {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	if (loadListPersonsAdaptative == null) {
	    try {
		File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
		loadListPersonsAdaptative = mapper.readValue(file, ListPersonAdaptative.class);
		logger.info("Le fichier est lu !");
		return loadListPersonsAdaptative;
	    } catch (IOException e) {
		logger.error("Fichier introuvable");
	    }
	}
	return loadListPersonsAdaptative;
    }

    /**
     * Get all persons
     * 
     * @return a list of all persons
     */
    @Override
    public ListPerson findAllPersons() {
	loadListPersons = loadPersons();
	return loadListPersons;
    }

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     */
    @Override
    public ListPerson findPersonsByCity(String city) {
	loadListPersons = loadPersons();
	listPersonsSorted = new ArrayList<Person>();
	for (Person p : loadListPersons.getListPersons()) {
	    if (p.getCity().equals(city)) {
		listPersonsSorted.add(p);
	    }
	}
	listPersonsToSend.setListPersons(listPersonsSorted);
	return listPersonsToSend;
    }

    /**
     * Get all persons
     * 
     * @return a list of all persons
     */
    @Override
    public ListPersonAdaptative findAllPersonAdaptative() {
	loadListPersonsAdaptative = loadPersonsAdaptative();
	return loadListPersonsAdaptative;
    }

    /**
     * Get all persons who live at an address
     * 
     * @param address address of a person
     * @return a list of persons who live at an address
     */
    @Override
    public ListPersonAdaptative findPersonAdaptativeByAddress(String address) {
	loadListPersonsAdaptative = loadPersonsAdaptative();
	listPersonsAdaptativeSorted = new ArrayList<PersonAdaptative>();
	for (PersonAdaptative p : loadListPersonsAdaptative.getListPersons()) {
	    if (p.getAddress().equals(address)) {
		listPersonsAdaptativeSorted.add(p);
	    }
	}
	listPersonsAdaptativeToSend.setListPersons(listPersonsAdaptativeSorted);
	return listPersonsAdaptativeToSend;
    }

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     */
    @Override
    public ListPersonAdaptative findPersonsAdaptativeByLastName(String lastName) {
	loadListPersonsAdaptative = loadPersonsAdaptative();
	listPersonsAdaptativeSorted = new ArrayList<PersonAdaptative>();
	for (PersonAdaptative p : loadListPersonsAdaptative.getListPersons()) {
	    if (p.getLastName().equals(lastName)) {
		listPersonsAdaptativeSorted.add(p);
	    }
	}
	listPersonsAdaptativeToSend.setListPersons(listPersonsAdaptativeSorted);
	return listPersonsAdaptativeToSend;
    }

}
