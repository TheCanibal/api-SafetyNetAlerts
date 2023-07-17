package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;

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
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    // List medical records to load
    private ListMedicalRecords loadListMedicalRecords;
    // List medical records to send
    private ListMedicalRecords listMedicalRecordsToSend = new ListMedicalRecords();
    // Logger
    private static Logger logger = LogManager.getLogger(MedicalRecordsRepositoryImpl.class);
    // Path file
    File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");

    /**
     * Read JSON file if it has not been read already and load it
     * 
     * @return a list of medical records from file
     */
    @Override
    public ListMedicalRecords loadMedicalRecords(boolean force) {
	mapper.findAndRegisterModules();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	if (loadListMedicalRecords == null || force) {
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
	listMedicalRecordsToSend = loadMedicalRecords(false);
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
	logger.debug("Le dossier médical à ajouter : {}", medicalRecord.toString());
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

		// read json file
		JsonNode parsedJson = mapper.readTree(file);
		// Get the array of persons
		ArrayNode personsArray = (ArrayNode) parsedJson.get("medicalrecords");
		// add person to the array
		personsArray.add(mapper.convertValue(newMedicalRecord, JsonNode.class));
		// write in the file
		writer.writeValue(file, parsedJson);
		loadMedicalRecords(true);
		return newMedicalRecord;
	    } else {
		throw new IllegalArgumentException("Un des champs est incorrect !");
	    }
	} catch (IOException e) {
	    logger.error("file not found");
	    return null;
	} catch (IllegalArgumentException e) {
	    if (medicalRecord.getFirstName() == null)
		logger.error("Le champ firstName ne doit pas être vide !");
	    if (medicalRecord.getLastName() == null)
		logger.error("Le champ lastName ne doit pas être vide !");
	    if (medicalRecord.getBirthdate() == null)
		logger.error("Le champ birthdate ne doit pas être vide !");
	    if (medicalRecord.getMedications() == null)
		logger.error("Le champ medications ne doit pas être vide !");
	    if (medicalRecord.getAllergies() == null)
		logger.error("Le champ allergies ne doit pas être vide !");

	    return null;
	}

    }

    /**
     * Update medicalrecords in the JSON File
     * 
     * @param medicalRecord medical record to update
     * @return updated medical record
     */
    @Override
    public MedicalRecords updateMedicalRecord(MedicalRecords medicalRecord) {
	try {
	    // Verify if the update is done
	    boolean update = false;
	    // JSON file
	    JsonNode parsedJson = mapper.readTree(file);
	    // Array to read in JSON file
	    ArrayNode medicalRecordsArray = (ArrayNode) parsedJson.get("medicalrecords");
	    // Object to get
	    ObjectNode object;
	    // Browse the array
	    for (int medciRec = 0; medciRec < medicalRecordsArray.size(); medciRec++) {
		// verify If the couple of First Name and Last Name is in the list
		if (medicalRecordsArray.get(medciRec).get("firstName").toString()
			.equals("\"" + medicalRecord.getFirstName() + "\"")
			&& medicalRecordsArray.get(medciRec).get("lastName").toString()
				.equals("\"" + medicalRecord.getLastName() + "\"")) {
		    object = (ObjectNode) medicalRecordsArray.get(medciRec);
		    logger.debug("Dossier médical à modifier : {}", object.toString());
		    if (medicalRecord.getBirthdate() != null
			    && medicalRecord.getBirthdate().matches("\\d{2}/\\d{2}/\\d{4}"))
			object.put("birthdate", medicalRecord.getBirthdate());
		    if (medicalRecord.getMedications() != null)
			object.putPOJO("medications", medicalRecord.getMedications());
		    if (medicalRecord.getAllergies() != null)
			object.putPOJO("allergies", medicalRecord.getAllergies());
		    mapper.writeValue(file, parsedJson);
		    logger.debug("Personne qui a été modifiée : {}", object.toString());
		    update = true;
		}
	    }
	    if (!update) {
		throw new IllegalArgumentException("Il n'y a aucune personne avec ce nom et prénom dans la liste !");
	    }
	    return medicalRecord;
	} catch (IOException e) {
	    logger.error("Le fichier n'a pas pu être lu");
	    return null;
	} catch (IllegalArgumentException e) {
	    logger.error("La personne {} {} n'apparaît pas dans la liste.", medicalRecord.getFirstName(),
		    medicalRecord.getLastName());
	    return null;
	}
    }

}
