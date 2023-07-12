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
    MedicalRecords saveMedicalRecord(MedicalRecords medicalRecord);

}
