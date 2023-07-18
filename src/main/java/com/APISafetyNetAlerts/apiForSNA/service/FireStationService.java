package com.APISafetyNetAlerts.apiForSNA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.APISafetyNetAlerts.apiForSNA.repository.FireStationRepository;

/**
 * 
 * Service firestation that take data from repository to send it to the
 * controller
 *
 */

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    /**
     * Get all firestations
     * 
     * @return list of all firestations
     */
    public ListFireStations getAllFireStations() {
	return fireStationRepository.findAllFirestation();
    }

    /**
     * Get firestations by number
     * 
     * @param station station number
     * @return list of firestations with the number
     */
    public ListFireStations getFirestationsByStationNumber(int station) {
	return fireStationRepository.findFireStationByStationNumber(station);
    }

    /**
     * Get firestations by list of number
     * 
     * @param stations list of stations
     * @return a list of firestations with the number
     */
    public ListFireStations getFireStationByListOfStationNumber(int[] stations) {
	return fireStationRepository.findFireStationByListOfStationNumber(stations);
    }

    /**
     * Add new firestation to the JSON File
     * 
     * @param firestation firestation to add
     * @return created firestation
     */
    public void createFirestation(FireStation firestation) {
	fireStationRepository.saveFirestation(firestation);
    }

    /**
     * Update station number of a fireStation
     * 
     * @param fireStation fireStation to update
     */
    public void updateFirestation(FireStation fireStation) {
	fireStationRepository.updateFirestation(fireStation);
    }

    /**
     * Delete a firestation with address, number or both
     * 
     * @param firestation firestation to delete
     */
    public void deleteFirestation(FireStation fireStation) {
	fireStationRepository.deleteFirestation(fireStation);
    }
}
