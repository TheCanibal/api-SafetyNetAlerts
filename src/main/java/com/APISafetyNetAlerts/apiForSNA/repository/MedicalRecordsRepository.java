package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;

@Repository
public interface MedicalRecordsRepository {
    ListMedicalRecords findAll() throws IOException;

    List<MedicalRecords> findByLastName(String lastName) throws IOException;
}
