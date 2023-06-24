package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.APISafetyNetAlerts.apiForSNA.repository.FireStationRepository;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public ListFireStations getPersons() throws IOException {
	return fireStationRepository.findAll();
    }
}
