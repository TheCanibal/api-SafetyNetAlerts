package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;

/**
 * Interface for the API to collect firestation data
 */

@Repository
public interface FireStationRepository {

    /**
     * Get all firestations
     * 
     * @return list of all firestations
     * @throws IOException
     */
    public ListFireStations findAllFirestation() throws IOException;

    /**
     * Get firestations by number
     * 
     * @param station station number
     * @return list of firestations with the number
     * @throws IOException
     */
    public ListFireStations findFireStationByStationNumber(int station) throws IOException;

    /**
     * Get firestations by list of number
     * 
     * @param stations list of stations
     * @return a list of firestations with the number
     * @throws IOException
     */
    public ListFireStations findFireStationByListOfStationNumber(int[] stations) throws IOException;
}
