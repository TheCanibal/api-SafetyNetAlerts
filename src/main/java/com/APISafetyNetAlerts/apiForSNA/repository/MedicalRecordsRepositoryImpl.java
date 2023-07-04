package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Interaction with the object medicalrecords in the data.json file
 *
 */

@Repository
public class MedicalRecordsRepositoryImpl implements MedicalRecordsRepository {
    ObjectMapper mapper = new ObjectMapper();
    List<MedicalRecords> listMedicalRecords;

    /**
     * Get all the medical records
     * 
     * @return a list of all medical records
     * @throws IOException
     */
    @Override
    public ListMedicalRecords findAllMedicalRecords() throws IOException {
	mapper.findAndRegisterModules();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListMedicalRecords medicalRecords = mapper.readValue(file, ListMedicalRecords.class);
	return medicalRecords;
    }

}
