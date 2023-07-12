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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

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

    /**
     * Add new medical record to the JSON File
     * 
     * @param medicalRecord medical record to add
     * @return created medical record
     */
    @Override
    public MedicalRecords saveMedicalRecord(MedicalRecords medicalRecord) {

	MedicalRecords newMedicalRecord = new MedicalRecords(medicalRecord.getFirstName(), medicalRecord.getLastName(),
		medicalRecord.getBirthdate(), medicalRecord.getMedications(), medicalRecord.getAllergies());
	try {
	    if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null
		    && medicalRecord.getBirthdate() != null && medicalRecord.getMedications() != null
		    && medicalRecord.getAllergies() != null) {
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
				.writeValueAsString(newMedicalRecord);
			logger.debug("Personn à ajouter : {}", json);
		    } catch (Exception e) {
			logger.error("Mapping error");
		    }
		}

		// Writer to write in Json
		ObjectWriter writer = mapper.writer();
		// Path file
		File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
		// read json file
		JsonNode parsedJson = mapper.readTree(file);
		// Get the array of persons
		ArrayNode personsArray = (ArrayNode) parsedJson.get("medicalrecords");
		// add person to the array
		personsArray.add(mapper.convertValue(newMedicalRecord, JsonNode.class));
		// write in the file
		writer.writeValue(file, parsedJson);
		return newMedicalRecord;
	    } else {
		throw new IllegalArgumentException("Un des champs est incorrect !");
	    }
	} catch (IOException e) {
	    logger.error("file not found");
	    return null;
	} catch (IllegalArgumentException e) {
	    if (medicalRecord.getFirstName() == null)
		logger.error("Le champ ne doit pas être vide !");
	    if (medicalRecord.getLastName() == null)
		logger.error("Le champ ne doit pas être vide !");
	    if (medicalRecord.getBirthdate() == null)
		logger.error("Le champ ne doit pas être vide !");
	    if (medicalRecord.getMedications() == null)
		logger.error("Le champ ne doit pas être vide !");
	    if (medicalRecord.getAllergies() == null)
		logger.error("Le champ ne doit pas être vide !");
	    logger.error("Aucune valeur");

	    return null;
	}

    }

}
