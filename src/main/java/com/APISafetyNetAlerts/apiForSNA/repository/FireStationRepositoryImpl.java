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
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    // List of firestations to load
    private ListFireStations loadListFirestations;
    // List of firestations to send
    private ListFireStations listFirestationsToSend = new ListFireStations();
    // Logger
    private static Logger logger = LogManager.getLogger(FireStationRepositoryImpl.class);
    // File path
    File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");

    /**
     * Read JSON file if it has not been read already and load it
     * 
     * @param force force to reload file
     * @return a list of firestations from file
     */
    @Override
    public ListFireStations loadFireStations(boolean force) {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	if (loadListFirestations == null || force) {
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
	loadListFirestations = loadFireStations(false);
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
	loadListFirestations = loadFireStations(false);
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
	loadListFirestations = loadFireStations(false);
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
	logger.debug("La station à ajouter : {}", firestation.toString());
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

		// read json file
		JsonNode parsedJson = mapper.readTree(file);
		// Get the array of persons
		ArrayNode personsArray = (ArrayNode) parsedJson.get("firestations");
		// add person to the array
		personsArray.add(mapper.convertValue(newFirestation, JsonNode.class));
		// write in the file
		writer.writeValue(file, parsedJson);
		loadFireStations(true);
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

    /**
     * Update station number of a fireStation
     * 
     * @param fireStation fireStation to update
     */
    @Override
    public void updateFirestation(FireStation fireStation) {
	try {
	    // Verify if the update is done
	    boolean update = false;
	    // JSON file
	    JsonNode parsedJson = mapper.readTree(file);
	    // Array to read in JSON file
	    ArrayNode firestationsArray = (ArrayNode) parsedJson.get("firestations");
	    // Object to get
	    ObjectNode object;
	    // if adress not null
	    if (fireStation.getAddress() != null) {
		// Browse the array
		for (int firestation = 0; firestation < firestationsArray.size(); firestation++) {
		    // verify If the address in the file is equal to the address of the firestation
		    // to modify
		    if (firestationsArray.get(firestation).get("address").toString()
			    .equals("\"" + fireStation.getAddress() + "\"")) {
			// get object to modify
			object = (ObjectNode) firestationsArray.get(firestation);
			logger.debug("Station à modifier : {}", object.toString());
			logger.debug("Addresse : {}", fireStation.getAddress());
			// station number different compare to the object and > 0
			if (fireStation.getStation() != object.get("station").asInt() && fireStation.getStation() > 0) {
			    object.put("station", fireStation.getStation());
			    logger.debug("Station qui a été modifiée : {}", object.toString());
			    mapper.writeValue(file, parsedJson);
			    update = true;
			} else {
			    logger.info("Le numéro de station {} est le même que celui à modifier ou <= 0 !",
				    fireStation.getStation());
			    update = true;
			}

		    }
		}
	    } else {
		throw new IllegalArgumentException("Addresse vide !");
	    }
	    if (!update) {
		throw new IllegalArgumentException("Il n'y a aucune station avec cette addresse dans la liste !");
	    }
	} catch (IOException e) {
	    logger.error("Le fichier n'a pas pu être lu");
	} catch (IllegalArgumentException e) {
	    logger.error("L'addresse est vide ou erronée !");
	}
    }
}
