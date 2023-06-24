package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    /**
     * Read - Get all firestations
     * 
     * @return - A List of firestations full filled
     * @throws IOException
     */
    @GetMapping("/firestations")
    public MappingJacksonValue getFireStations() throws IOException {
	ListFireStations listFireStations = fireStationService.getFireStations();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamique", monFiltre);
	MappingJacksonValue fireStationsFiltres = new MappingJacksonValue(listFireStations);
	fireStationsFiltres.setFilters(filtres);
	return fireStationsFiltres;
    }

    /**
     * Read - Get all firestations
     * 
     * @return - A List of firestations full filled
     * @throws IOException
     */
    @GetMapping("/firestationsFilter")
    public MappingJacksonValue getFilteredFireStations() throws IOException {
	ListFireStations listFireStations = fireStationService.getFireStations();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("station");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamique", monFiltre);
	MappingJacksonValue fireStationsFiltres = new MappingJacksonValue(listFireStations);
	fireStationsFiltres.setFilters(filtres);
	return fireStationsFiltres;
    }
}
