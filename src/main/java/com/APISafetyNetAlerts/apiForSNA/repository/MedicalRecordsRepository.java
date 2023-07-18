package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;

/**
 * Interface for the API to collect medical records data
 */
@Repository
public interface MedicalRecordsRepository {

    /**
     * Read JSON file if it has not been read already and load it
     * 
     * @return a list of medical records from file
     */
    public ListMedicalRecords loadMedicalRecords(boolean force);

    /**
     * Get all the medical records
     * 
     * @return a list of all medical records
     * @throws IOException
     */
    public ListMedicalRecords findAllMedicalRecords();

    /**
     * Add new medical record to the JSON File
     * 
     * @param medicalRecord medical record to add
     * @return created medical record
     */
    public void saveMedicalRecord(MedicalRecords medicalRecord);

    /**
     * Update medicalrecords in the JSON File
     * 
     * @param medicalRecord medical record to update
     * @return updated medical record
     */
    public void updateMedicalRecord(MedicalRecords medicalRecord);

    /**
     * Delete medical record in the JSON file
     * 
     * @param medicalRecord medical record to delete
     */
    public void deleteMedicalRecord(MedicalRecords medicalRecord);
}
