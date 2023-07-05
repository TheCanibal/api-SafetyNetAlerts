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
import com.fasterxml.jackson.databind.ObjectMapper;

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
		logger.info("Le fichier est lu !");
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

}
