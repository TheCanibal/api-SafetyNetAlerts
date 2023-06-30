package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;

@Repository
public interface FireStationRepository {
    public ListFireStations findAll() throws IOException;

    public ListFireStations findByStation(int station) throws IOException;

    public ListFireStations findByAddress(String address) throws IOException;
}
