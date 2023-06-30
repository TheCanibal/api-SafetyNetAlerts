package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListFireStations;
import com.APISafetyNetAlerts.apiForSNA.repository.FireStationRepository;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public ListFireStations getFireStations() throws IOException {
	return fireStationRepository.findAll();
    }

    public ListFireStations getFirestationsByStationNumber(int station) throws IOException {
	return fireStationRepository.findByStation(station);
    }

    public ListFireStations getFirestationsByStationAddress(String address) throws IOException {
	return fireStationRepository.findByAddress(address);
    }

    public List<PersonAdaptative> getStationNumberByListPersons(List<PersonAdaptative> listPersons) throws IOException {

	List<PersonAdaptative> listToReturn = new ArrayList<PersonAdaptative>();

	List<FireStation> listFireStation = fireStationRepository.findAll().getListFirestation();

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
