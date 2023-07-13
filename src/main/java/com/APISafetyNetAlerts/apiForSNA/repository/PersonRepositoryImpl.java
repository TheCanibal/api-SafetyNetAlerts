package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.DynamicPerson;
import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * 
 * Interaction with the object persons in the data.json file
 *
 */

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    // ObjectMapper to be able to deserialize JSON
    private ObjectMapper mapper = new ObjectMapper().addMixIn(Person.class, DynamicPerson.class);
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
    @Override
    public ListPerson loadPersons(boolean force) {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	if (loadListPersons == null) {
	    try {
		File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
		loadListPersons = mapper.readValue(file, ListPerson.class);
		logger.info("Le fichier est lu pour récupérer les personnes !");
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
    @Override
    public ListPersonAdaptative loadPersonsAdaptative(boolean force) {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	if (loadListPersonsAdaptative == null) {
	    try {
		File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
		loadListPersonsAdaptative = mapper.readValue(file, ListPersonAdaptative.class);
		logger.info("Le fichier est lu pour récupérer les personnes dont on veut ajouter des informations!");
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
	// Read the file and fill the list if it's not
	loadListPersons = loadPersons(false);
	return loadListPersons;
    }

    /**
     * Get all persons
     * 
     * @return a list of all persons
     */
    @Override
    public ListPersonAdaptative findAllPersonAdaptative() {
	// Read the file and fill the list if it's not
	loadListPersonsAdaptative = loadPersonsAdaptative(false);
	return loadListPersonsAdaptative;
    }

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     */
    @Override
    public ListPerson findPersonsByCity(String city) {
	// Read the file and fill the list if it's not
	loadListPersons = loadPersons(false);
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
     * Get all persons who live at an address
     * 
     * @param address address of a person
     * @return a list of persons who live at an address
     */
    @Override
    public ListPersonAdaptative findPersonAdaptativeByAddress(String address) {
	// Read the file and fill the list if it's not
	loadListPersonsAdaptative = loadPersonsAdaptative(false);
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
     * Get all persons with the last name
     * 
     * @param lastName last name of persons
     * @return a list of persons with the last name
     */
    @Override
    public ListPersonAdaptative findPersonsAdaptativeByLastName(String lastName) {
	// Read the file and fill the list if it's not
	loadListPersonsAdaptative = loadPersonsAdaptative(false);
	listPersonsAdaptativeSorted = new ArrayList<PersonAdaptative>();
	for (PersonAdaptative p : loadListPersonsAdaptative.getListPersons()) {
	    if (p.getLastName().equals(lastName)) {
		listPersonsAdaptativeSorted.add(p);
	    }
	}
	listPersonsAdaptativeToSend.setListPersons(listPersonsAdaptativeSorted);
	return listPersonsAdaptativeToSend;
    }

    /**
     * Get all persons with last name AND first name
     * 
     * @param lastName  last name of a person
     * @param firstName first name of a person
     * @return a list of persons with last name AND first name
     */
    @Override
    public ListPersonAdaptative findPersonsAdaptativeByFirstNameAndLastName(String firstName, String lastName) {
	// Read the file and fill the list if it's not
	loadListPersonsAdaptative = loadPersonsAdaptative(false);
	listPersonsAdaptativeSorted = new ArrayList<PersonAdaptative>();
	for (PersonAdaptative p : loadListPersonsAdaptative.getListPersons()) {
	    if (p.getLastName().equals(lastName) && p.getFirstName().equals(firstName)) {
		listPersonsAdaptativeSorted.add(p);
	    }
	}
	listPersonsAdaptativeToSend.setListPersons(listPersonsAdaptativeSorted);
	return listPersonsAdaptativeToSend;
    }

    /**
     * Add new person to the JSON File
     * 
     * @param person person to add
     * @return created person
     */
    @Override
    public Person savePerson(Person person) {

	Person newPerson = new Person(person.getFirstName(), person.getLastName(), person.getAddress(),
		person.getCity(), person.getZip(), person.getPhone(), person.getEmail());
	logger.debug("La personne à ajouter : {}", person.toString());
	try {
	    if (person.getFirstName() != null && person.getLastName() != null && person.getAddress() != null
		    && person.getCity() != null && person.getZip() > 0 && person.getPhone() != null
		    && person.getEmail() != null) {
		// Filter rules to apply to the bean
		SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAll();
		// This allow the filter to apply to all the beans with dynamic filters
		SimpleFilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
		// set filter
		mapper.setFilterProvider(filtres);
		// Log the result if debug mode is enabled
		if (logger.isDebugEnabled()) {
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
			String json = objectMapper.setFilterProvider(filtres).writerWithDefaultPrettyPrinter()
				.writeValueAsString(newPerson);
			logger.debug("Personn à ajouter : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}
		// Writer to write in Json
		ObjectWriter writer = mapper.writer();
		// Path file
		File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
		// read json file
		JsonNode parsedJson = mapper.readTree(file);
		// Get the array of persons
		ArrayNode personsArray = (ArrayNode) parsedJson.get("persons");
		// add person to the array
		personsArray.add(mapper.convertValue(newPerson, JsonNode.class));
		// write in the file
		writer.writeValue(file, parsedJson);
		return newPerson;
	    } else {
		throw new IllegalArgumentException("Un des champs est incorrect !");
	    }
	} catch (IOException e) {
	    logger.error("file not found");
	    return null;
	} catch (IllegalArgumentException e) {
	    if (person.getFirstName() == null)
		logger.error("Le champ prénom ne doit pas être vide !");
	    if (person.getLastName() == null)
		logger.error("Le champ nom ne doit pas être vide !");
	    if (person.getAddress() == null)
		logger.error("Le champ addresse ne doit pas être vide !");
	    if (person.getCity() == null)
		logger.error("Le champ ville ne doit pas être vide !");
	    if (person.getZip() <= 0)
		logger.error("Le champ code postal ne doit pas être vide !");
	    if (person.getPhone() == null)
		logger.error("Le champ numéro de téléphone ne doit pas être vide !");
	    if (person.getEmail() == null)
		logger.error("Le champ email ne doit pas être vide !");
	    return null;
	}

    }
}
