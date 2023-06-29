package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListPersons;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.repository.FireStationRepository;
import com.APISafetyNetAlerts.apiForSNA.repository.PersonRepository;

@Service
public class FirestationPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationRepository firestationRepository;

    public ListPersons getPersonsAtFireStationAddress(int stationNumber) throws IOException {
	List<FireStation> listFireStations = firestationRepository.findByStation(stationNumber).getListFirestation();
	List<Person> listPersons = personRepository.findAll().getListPersons();
	List<Person> emptyList = new ArrayList<Person>();
	ListPersons listPersonsToReturn = new ListPersons();
	emptyList.addAll(listPersons.stream()
		.filter(p -> listFireStations.stream().anyMatch(fs -> p.getAddress().equals(fs.getAddress())))
		.collect(Collectors.toList()));
	listPersonsToReturn.setListPersons(emptyList);
	return listPersonsToReturn;
    }

}
