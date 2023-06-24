package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;

@Repository
public interface FireStationRepository {
    ListFireStations findAll() throws IOException;
}
