package com.APISafetyNetAlerts.apiForSNA.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.APISafetyNetAlerts.apiForSNA.repository.FireStationRepository;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;

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

    public ListFireStations getFireStations() {
	return fireStationRepository.findAllFirestation();
    }

    public ListFireStations getFirestationsByStationNumber(int station) {
	return fireStationRepository.findFireStationByStationNumber(station);
    }

    public List<PersonAdaptative> getStationNumberByListPersons(List<PersonAdaptative> listPersons) {

	List<PersonAdaptative> listToReturn = new ArrayList<PersonAdaptative>();

	List<FireStation> listFireStation = fireStationRepository.findAllFirestation().getListFirestation();

	for (PersonAdaptative p : listPersons) {
	    for (FireStation fs : listFireStation) {
		if (p.getAddress().equals(fs.getAddress())) {
		    p.setFirestationNumber(fs.getStation());
		    listToReturn.add(p);
		}
	    }
	}

	return listToReturn;
    }

}
