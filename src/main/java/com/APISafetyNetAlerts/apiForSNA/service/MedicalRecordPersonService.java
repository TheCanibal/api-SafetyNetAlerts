package com.APISafetyNetAlerts.apiForSNA.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.APISafetyNetAlerts.apiForSNA.repository.MedicalRecordsRepository;
import com.APISafetyNetAlerts.apiForSNA.repository.PersonRepository;

public class MedicalRecordPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordsRepository medicalRecordRepository;

}
