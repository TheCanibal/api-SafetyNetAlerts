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
    List<MedicalRecords> listMedicalToFill = new ArrayList<MedicalRecords>();

    public ListMedicalRecords findAll() throws IOException {
	mapper.findAndRegisterModules();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListMedicalRecords medicalRecords = mapper.readValue(file, ListMedicalRecords.class);
	return medicalRecords;
    }

    @Override
    public List<MedicalRecords> findByLastName(String lastName) throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	List<MedicalRecords> medicalRecords = mapper.readValue(file, ListMedicalRecords.class).getListMedicalrecords();
	for (MedicalRecords mr : medicalRecords) {
	    if (mr.getLastName().equals(lastName)) {
		listMedicalToFill.add(mr);
	    }

	}
	return listMedicalToFill;
    }

}
