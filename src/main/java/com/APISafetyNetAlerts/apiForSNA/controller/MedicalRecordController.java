package com.APISafetyNetAlerts.apiForSNA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Add new medical record to the JSON File
     * 
     * @param medicalRecord medical record to add
     * @return created medical record
     */
    @PostMapping("/medicalRecord")
    public MedicalRecords createMedicalRecord(@RequestBody MedicalRecords medicalRecord) {
	return medicalRecordService.createMedicalRecord(medicalRecord);
    }
}
