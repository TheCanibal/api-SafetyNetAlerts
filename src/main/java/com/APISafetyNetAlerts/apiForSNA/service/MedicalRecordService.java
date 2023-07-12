package com.APISafetyNetAlerts.apiForSNA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.repository.MedicalRecordsRepository;

/**
 * 
 * Service medicalrecord that take data from repository to send it to the
 * controller
 *
 */

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordsRepository medicalRecordRepository;

    /**
     * Get all the medical records
     * 
     * @return a list of all medical records
     */
    public ListMedicalRecords getMedicalRecords() {
	return medicalRecordRepository.findAllMedicalRecords();
    }

    /**
     * Add new medical record to the JSON File
     * 
     * @param medicalRecord medical record to add
     * @return created medical record
     */
    public MedicalRecords createMedicalRecord(MedicalRecords medicalRecord) {
	return medicalRecordRepository.saveMedicalRecord(medicalRecord);
    }

}
