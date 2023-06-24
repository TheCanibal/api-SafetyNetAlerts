package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;

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
    public ListFireStations getPersons() throws IOException {
	return fireStationService.getPersons();
    }
}
