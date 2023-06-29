package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;

@Repository
public interface MedicalRecordsRepository {
    ListMedicalRecords findAll() throws IOException;

    ListMedicalRecords findByLastName(String lastName) throws IOException;
}
