package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {
    ObjectMapper mapper = new ObjectMapper();

    public ListFireStations findAll() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListFireStations persons = mapper.readValue(file, ListFireStations.class);
	return persons;
    }
}
