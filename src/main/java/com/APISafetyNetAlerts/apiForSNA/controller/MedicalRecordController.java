package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Read - Get all persons
     * 
     * @return - A list of persons full filled
     * @throws IOException
     */
    @GetMapping("/medicalrecords")
    public ListMedicalRecords getPersons() throws IOException {
	return medicalRecordService.getPersons();
    }
}
