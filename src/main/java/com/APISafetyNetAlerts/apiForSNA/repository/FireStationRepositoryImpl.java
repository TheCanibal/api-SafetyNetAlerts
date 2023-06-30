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

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {
    ObjectMapper mapper = new ObjectMapper();
    List<FireStation> listFiresationsSorted;

    public ListFireStations findAll() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListFireStations persons = mapper.readValue(file, ListFireStations.class);
	return persons;
    }

    public ListFireStations findByStation(int station) throws IOException {
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

    public ListFireStations findByAddress(String address) throws IOException {

	return null;
    }

}
