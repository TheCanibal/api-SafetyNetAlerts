package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;

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

}
