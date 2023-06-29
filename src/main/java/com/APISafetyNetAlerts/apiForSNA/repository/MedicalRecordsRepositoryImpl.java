package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class MedicalRecordsRepositoryImpl implements MedicalRecordsRepository {
    ObjectMapper mapper = new ObjectMapper();
    ListMedicalRecords listMedicalToFill;
    List<MedicalRecords> listMedicalRecords;

    public ListMedicalRecords findAll() throws IOException {
	mapper.findAndRegisterModules();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListMedicalRecords medicalRecords = mapper.readValue(file, ListMedicalRecords.class);
	return medicalRecords;
    }

    public ListMedicalRecords findByLastName(String lastName) throws IOException {
	listMedicalToFill = new ListMedicalRecords();
	listMedicalRecords = new ArrayList<MedicalRecords>();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListMedicalRecords medicalRecords = mapper.readValue(file, ListMedicalRecords.class);
	for (MedicalRecords mr : medicalRecords.getListMedicalrecords()) {
	    if (mr.getLastName().equals(lastName)) {
		listMedicalRecords.add(mr);
	    }
	}
	listMedicalToFill.setListMedicalrecords(listMedicalRecords);
	return listMedicalToFill;
    }

}
