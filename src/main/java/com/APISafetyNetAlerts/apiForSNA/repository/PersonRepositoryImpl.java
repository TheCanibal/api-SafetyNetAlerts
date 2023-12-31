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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
    // File to read
    File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");

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
    public void savePerson(Person person) {
	// Person to add
	Person newPerson = new Person(person.getFirstName(), person.getLastName(), person.getAddress(),
		person.getCity(), person.getZip(), person.getPhone(), person.getEmail());
	logger.debug("La personne à ajouter : {}", person.toString());
	try {
	    // if all fields are filled
	    if (person.getFirstName() != null && person.getLastName() != null && person.getAddress() != null
		    && person.getCity() != null && person.getZip() > 0 && person.getPhone() != null
		    && person.getEmail() != null) {

		// Log the result if debug mode is enabled
		if (logger.isDebugEnabled()) {
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
			String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newPerson);
			logger.debug("Personne à ajouter : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}
		// Writer to write in Json
		ObjectWriter writer = mapper.writer();
		// read json file
		JsonNode parsedJson = mapper.readTree(file);
		// Get the array of persons
		ArrayNode personsArray = (ArrayNode) parsedJson.get("persons");
		// add person to the array
		personsArray.add(mapper.convertValue(newPerson, JsonNode.class));
		// write in the file
		writer.writeValue(file, parsedJson);
		loadPersons(true);
	    } else {
		throw new IllegalArgumentException("Un des champs est incorrect !");
	    }
	} catch (IOException e) {
	    logger.error("file not found");
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
	}

    }

    /**
     * Update person in the JSON File
     * 
     * @param person person to update
     * @return updated person
     */
    @Override
    public void updatePerson(Person person) {
	try {
	    // Verify if the update is done
	    boolean update = false;
	    // JSON file
	    JsonNode parsedJson = mapper.readTree(file);
	    // Array to read in JSON file
	    ArrayNode personsArray = (ArrayNode) parsedJson.get("persons");
	    // Object to get
	    ObjectNode object;
	    // Browse the array
	    for (int prsn = 0; prsn < personsArray.size(); prsn++) {
		// verify If the couple of First Name and Last Name is in the list
		if (personsArray.get(prsn).get("firstName").toString().equals("\"" + person.getFirstName() + "\"")
			&& personsArray.get(prsn).get("lastName").toString()
				.equals("\"" + person.getLastName() + "\"")) {
		    object = (ObjectNode) personsArray.get(prsn);
		    logger.debug("Personne à modifier : {}", object.toString());
		    if (person.getAddress() != null)
			object.put("address", person.getAddress());
		    if (person.getCity() != null)
			object.put("city", person.getCity());
		    if (person.getZip() > 0)
			object.put("zip", person.getZip());
		    if (person.getPhone() != null)
			object.put("phone", person.getPhone());
		    if (person.getEmail() != null)
			object.put("email", person.getEmail());
		    mapper.writeValue(file, parsedJson);
		    logger.debug("Personne qui a été modifiée : {}", object.toString());
		    loadPersons(true);
		    update = true;
		}
	    }
	    if (!update) {
		throw new IllegalArgumentException("Il n'y a aucune personne avec ce nom et prénom dans la liste !");
	    }
	} catch (IOException e) {
	    logger.error("Le fichier n'a pas pu être lu");
	} catch (IllegalArgumentException e) {
	    logger.error("La personne {} {} n'apparaît pas dans la liste.", person.getFirstName(),
		    person.getLastName());
	}
    }

    /**
     * Delete person in the JSON file
     * 
     * @param person person to delete
     * @return person deleted
     */
    @Override
    public void deletePerson(Person person) {
	try {
	    // Verify if the update is done
	    boolean delete = false;
	    // JSON file
	    JsonNode parsedJson = mapper.readTree(file);
	    // Array to read in JSON file
	    ArrayNode personsArray = (ArrayNode) parsedJson.get("persons");
	    if (person.getFirstName() != null && person.getLastName() != null) {
		// Browse the array
		for (int prsn = 0; prsn < personsArray.size(); prsn++) {
		    // verify If the couple of First Name and Last Name is in the list
		    if (personsArray.get(prsn).get("firstName").toString().equals("\"" + person.getFirstName() + "\"")
			    && personsArray.get(prsn).get("lastName").toString()
				    .equals("\"" + person.getLastName() + "\"")) {
			logger.debug("Personne à supprimer : {}", personsArray.get(prsn).toString());
			personsArray.remove(prsn);
			mapper.writeValue(file, parsedJson);
			loadPersons(true);
			delete = true;
		    }
		}
	    } else {
		throw new IllegalArgumentException("Il faut un nom et un prénom !");
	    }
	    if (!delete) {
		throw new NullPointerException("Aucune personne avec ce nom et prénom dans la liste !");
	    }
	} catch (IOException e) {
	    logger.error("Le fichier n'a pas pu être lu");
	} catch (IllegalArgumentException e) {
	    logger.error("La personne {} {} n'apparaît pas dans la liste.", person.getFirstName(),
		    person.getLastName());
	} catch (NullPointerException e) {
	    logger.error("Il faut un nom et un prénom pour pouvoir supprimer un objet !");
	}
    }
}
