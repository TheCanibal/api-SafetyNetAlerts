package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class MedicalRecordsRepositoryImpl {
    ObjectMapper mapper = new ObjectMapper();

    public ListMedicalRecords findAll() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\dev\\apiSNA\\apiSNA\\src\\main\\resources\\data.json");
	ListMedicalRecords persons = mapper.readValue(file, ListMedicalRecords.class);
	return persons;
    }

}
