package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * 
 * Interaction with the object firestations in the data.json file
 *
 */

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {
    // Object Mapper to be able to deserialize JSON
    private ObjectMapper mapper = new ObjectMapper();
    // List of firestations to sort and to set
    private List<FireStation> listFirestationsSorted;
    // List of firestations to load only one time
    private ListFireStations loadListFirestations;
    // List of firestations to send
    private ListFireStations listFirestationsToSend = new ListFireStations();
    // Logger
    private static Logger logger = LogManager.getLogger(FireStationRepositoryImpl.class);

    /**
     * Read JSON file if it has not been read already and load it
     * 
     * @return a list of firestations from file
     */
    public ListFireStations loadFireStations() {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	if (loadListFirestations == null) {
	    try {
		loadListFirestations = mapper.readValue(file, ListFireStations.class);
		logger.info("Le fichier est lu pour récupérer les Firestations!");
		return loadListFirestations;
	    } catch (IOException e) {
		logger.error("Fichier introuvable");
	    }
	}
	return loadListFirestations;
    }

    /**
     * Get all firestations
     * 
     * @return list of all firestations
     */
    @Override
    public ListFireStations findAllFirestation() {
	// Read the file and fill the list if it's not
	loadListFirestations = loadFireStations();
	return loadListFirestations;
    }

    /**
     * Get firestations by number
     * 
     * @param station station number
     * @return list of firestations with the number
     */
    @Override
    public ListFireStations findFireStationByStationNumber(int station) {
	// Read the file and fill the list if it's not
	loadListFirestations = loadFireStations();
	listFirestationsSorted = new ArrayList<FireStation>();
	for (FireStation fs : loadListFirestations.getListFirestation()) {
	    if (fs.getStation() == station) {
		listFirestationsSorted.add(fs);
	    }
	}
	listFirestationsToSend.setListFirestation(listFirestationsSorted);
	return listFirestationsToSend;

    }

    /**
     * Get firestations by list of number
     * 
     * @param stations list of stations
     * @return a list of firestations with the number
     */
    @Override
    public ListFireStations findFireStationByListOfStationNumber(int[] stations) {
	// Read the file and fill the list if it's not
	loadListFirestations = loadFireStations();
	listFirestationsSorted = new ArrayList<FireStation>();
	for (FireStation fs : loadListFirestations.getListFirestation()) {
	    for (int i : stations) {
		if (fs.getStation() == i) {
		    listFirestationsSorted.add(fs);
		}
	    }
	}
	listFirestationsToSend.setListFirestation(listFirestationsSorted);
	return listFirestationsToSend;
    }

    /**
     * Add new firestation to the JSON File
     * 
     * @param firestation firestation to add
     * @return created firestation
     */
    @Override
    public FireStation saveFirestation(FireStation firestation) {

	FireStation newFirestation = new FireStation(firestation.getAddress(), firestation.getStation());
	try {
	    if (firestation.getAddress() != null && firestation.getStation() > 0) {
		logger.info("L'address est {}", firestation.getAddress());
		logger.info("Le numéro de station est {}", firestation.getStation());
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
				.writeValueAsString(newFirestation);
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
		ArrayNode personsArray = (ArrayNode) parsedJson.get("firestations");
		// add person to the array
		personsArray.add(mapper.convertValue(newFirestation, JsonNode.class));
		// write in the file
		writer.writeValue(file, parsedJson);
		return newFirestation;
	    } else {
		throw new IllegalArgumentException("Un des champs est incorrect !");
	    }
	} catch (IOException e) {
	    logger.error("file not found");
	    return null;
	} catch (IllegalArgumentException e) {
	    if (firestation.getAddress() == null)
		logger.error("L'adresse {} est incorrecte !", firestation.getAddress());
	    if (firestation.getStation() <= 0)
		logger.error("Le numéro de caserne {} est incorrect !", firestation.getStation());
	    return null;
	}

    }

}
