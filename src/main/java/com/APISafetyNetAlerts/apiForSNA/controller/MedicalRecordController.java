package com.APISafetyNetAlerts.apiForSNA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Read - Get all medical records
     * 
     * @return all medical records
     */
    @GetMapping("/medicalRecords")
    public ListMedicalRecords getAllMedicalRecord() {
	return medicalRecordService.getMedicalRecords();
    }

    /**
     * Create - Add new medical record to the JSON File
     * 
     * @param medicalRecord medical record to add
     * @return created medical record
     */
    @PostMapping("/medicalRecord")
    public MedicalRecords createMedicalRecord(@RequestBody MedicalRecords medicalRecord) {
	return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    /**
     * Update medicalrecords in the JSON File
     * 
     * @param medicalRecord medical record to update
     * @return updated medical record
     */
    @PutMapping("/medicalRecord")
    public MedicalRecords updateMedicalRecord(@RequestBody MedicalRecords medicalRecord) {
	return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    /**
     * Delete medical record in the JSON file
     * 
     * @param medicalRecord medical record to delete
     */
    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@RequestBody MedicalRecords medicalRecord) {
	medicalRecordService.deleteMedicalRecord(medicalRecord);
    }
}
