package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.repository.MedicalRecordsRepository;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordsRepository medicalRecordRepository;

    public ListMedicalRecords getMedicalRecords() throws IOException {
	return medicalRecordRepository.findAll();
    }

    public List<MedicalRecords> getMedicalRecordsByLastName(String lastName) throws IOException {
	return medicalRecordRepository.findByLastName(lastName);
    }
}
