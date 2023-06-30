package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;

@Repository
public interface MedicalRecordsRepository {
    public ListMedicalRecords findAll() throws IOException;

    public ListMedicalRecords findByLastName(String lastName) throws IOException;
}
