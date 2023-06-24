package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.service.MedicalRecordService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

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
    public MappingJacksonValue getMedicalRecords() throws IOException {
	ListMedicalRecords listMedicalRecords = medicalRecordService.getMedicalRecords();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedic", monFiltre);
	MappingJacksonValue medicalRecordsFiltres = new MappingJacksonValue(listMedicalRecords);
	medicalRecordsFiltres.setFilters(filtres);
	return medicalRecordsFiltres;
    }

    /**
     * Read - Get all persons
     * 
     * @return - A list of persons full filled
     * @throws IOException
     */
    @GetMapping("/medicalrecordsFiltre")
    public MappingJacksonValue getFilteredMedicalRecords() throws IOException {
	ListMedicalRecords listMedicalRecords = medicalRecordService.getMedicalRecords();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("firstName", "medications");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiqueMedic", monFiltre);
	MappingJacksonValue medicalRecordsFiltres = new MappingJacksonValue(listMedicalRecords);
	medicalRecordsFiltres.setFilters(filtres);
	return medicalRecordsFiltres;
    }
}
