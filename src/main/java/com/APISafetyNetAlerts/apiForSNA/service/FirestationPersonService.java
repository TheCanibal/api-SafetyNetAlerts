package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.repository.FireStationRepository;
import com.APISafetyNetAlerts.apiForSNA.repository.PersonRepository;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;

/**
 * 
 * Service person and firestation that take data from repository to send it to
 * the controller
 *
 */

@Service
public class FirestationPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationRepository firestationRepository;

    public List<Person> getPersonsAtFireStationAddress(int stationNumber) throws IOException {
	List<FireStation> listFireStations = firestationRepository.findFireStationByStationNumber(stationNumber)
		.getListFirestation();
	List<Person> listPersons = personRepository.findAllPersons().getListPersons();
	List<Person> emptyList = new ArrayList<Person>();
	emptyList.addAll(listPersons.stream()
		.filter(p -> listFireStations.stream().anyMatch(fs -> p.getAddress().equals(fs.getAddress())))
		.collect(Collectors.toList()));
	return emptyList;
    }

    public List<PersonAdaptative> getPersonsByListFireStations(int[] stations) throws IOException {

	List<PersonAdaptative> listToReturn = new ArrayList<PersonAdaptative>();

	List<PersonAdaptative> listAllPersons = personRepository.findAllPersonAdaptative().getListPersons();

	List<FireStation> listFireStation = firestationRepository.findFireStationByListOfStationNumber(stations)
		.getListFirestation();

	for (PersonAdaptative p : listAllPersons) {
	    for (FireStation fs : listFireStation) {
		if (p.getAddress().equals(fs.getAddress())) {
		    listToReturn.add(p);
		}
	    }
	}

	return listToReturn;

    }

}
