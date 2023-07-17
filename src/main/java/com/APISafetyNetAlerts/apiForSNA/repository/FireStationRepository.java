package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;

/**
 * Interface for the API to collect firestation data
 */

@Repository
public interface FireStationRepository {

    /**
     * Read JSON file if it has not been read already and load it
     * 
     * @return a list of firestations from file
     */
    public ListFireStations loadFireStations(boolean force);

    /**
     * Get all firestations
     * 
     * @return list of all firestations
     * @throws IOException
     */
    public ListFireStations findAllFirestation();

    /**
     * Get firestations by number
     * 
     * @param station station number
     * @return list of firestations with the number
     * @throws IOException
     */
    public ListFireStations findFireStationByStationNumber(int station);

    /**
     * Get firestations by list of number
     * 
     * @param stations list of stations
     * @return a list of firestations with the number
     * @throws IOException
     */
    public ListFireStations findFireStationByListOfStationNumber(int[] stations);

    /**
     * Add new person to the JSON File
     * 
     * @param person person to add
     * @return created person
     */
    FireStation saveFirestation(FireStation firestation);

    /**
     * Update station number of a fireStation
     * 
     * @param fireStation fireStation to update
     */
    public void updateFirestation(FireStation fireStation);

    /**
     * Delete a firestation
     * 
     * @param firestation firestation to delete
     */
    public void deleteFirestation(FireStation fireStation);
}
