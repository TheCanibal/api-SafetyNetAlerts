package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    // ObjectMapper to be able to deserialize JSON
    private ObjectMapper mapper = new ObjectMapper();
    // List medical records to load only one time
    private ListMedicalRecords loadListMedicalRecords;
    // List medical records to send
    private ListMedicalRecords listMedicalRecordsToSend = new ListMedicalRecords();
    // List medical records to sort and to set
    private List<MedicalRecords> listMedicalRecords;
    // Logger
    private static Logger logger = LogManager.getLogger(MedicalRecordsRepositoryImpl.class);

    /**
     * Read JSON file if it has not been read already and load it
     * 
     * @return a list of medical records from file
     */
    public ListMedicalRecords loadMedicalRecords() {
	mapper.findAndRegisterModules();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	if (loadListMedicalRecords == null) {
	    try {
		File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
		loadListMedicalRecords = mapper.readValue(file, ListMedicalRecords.class);
		logger.info("Le fichier est lu pour récupérer les antécédents médicaux!");
		return loadListMedicalRecords;
	    } catch (IOException e) {
		logger.error("Fichier introuvable");
	    }
	}
	return loadListMedicalRecords;
    }

    /**
     * Get all the medical records
     * 
     * @return a list of all medical records
     */
    @Override
    public ListMedicalRecords findAllMedicalRecords() {
	// Read the file and fill the list if it's not
	listMedicalRecordsToSend = loadMedicalRecords();
	return listMedicalRecordsToSend;
    }

}
