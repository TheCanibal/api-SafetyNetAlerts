package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    ObjectMapper mapper = new ObjectMapper();
    List<FireStation> listFiresationsSorted;

    /**
     * Get all firestations
     * 
     * @return list of all firestations
     * @throws IOException
     */
    @Override
    public ListFireStations findAllFirestation() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListFireStations persons = mapper.readValue(file, ListFireStations.class);
	return persons;
    }

    /**
     * Get firestations by number
     * 
     * @param station station number
     * @return list of firestations with the number
     * @throws IOException
     */
    @Override
    public ListFireStations findFireStationByStationNumber(int station) throws IOException {
	listFiresationsSorted = new ArrayList<FireStation>();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListFireStations listFirestations = mapper.readValue(file, ListFireStations.class);
	for (FireStation fs : listFirestations.getListFirestation()) {
	    if (fs.getStation() == station) {
		listFiresationsSorted.add(fs);
	    }
	}
	listFirestations.setListFirestation(listFiresationsSorted);
	return listFirestations;
    }

    /**
     * Get firestations by list of number
     * 
     * @param stations list of stations
     * @return a list of firestations with the number
     * @throws IOException
     */
    @Override
    public ListFireStations findFireStationByListOfStationNumber(int[] stations) throws IOException {
	listFiresationsSorted = new ArrayList<FireStation>();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListFireStations listFirestations = mapper.readValue(file, ListFireStations.class);
	for (FireStation fs : listFirestations.getListFirestation()) {
	    for (int i : stations) {
		if (fs.getStation() == i) {
		    listFiresationsSorted.add(fs);
		}
	    }
	}
	listFirestations.setListFirestation(listFiresationsSorted);
	return listFirestations;
    }

}
