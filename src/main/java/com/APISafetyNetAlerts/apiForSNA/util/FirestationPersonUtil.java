package com.APISafetyNetAlerts.apiForSNA.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;

/**
 * class Util to make calculation with firestation and person
 * 
 *
 */
@Component
public class FirestationPersonUtil {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    /**
     * Get a list of persons from a list of firestation sort by number, thanks to
     * the address
     * 
     * @param stationNumber
     * @return a list of persons who the address is covered by the firestation
     */
    public List<Person> getPersonsByFireStationNumber(int stationNumber) {
	List<FireStation> listFireStations = fireStationService.getFirestationsByStationNumber(stationNumber)
		.getListFirestation();
	List<Person> listPersons = personService.getAllPersons().getListPersons();
	List<Person> listToFill = new ArrayList<Person>();
	listToFill.addAll(listPersons.stream()
		.filter(p -> listFireStations.stream().anyMatch(fs -> p.getAddress().equals(fs.getAddress())))
		.collect(Collectors.toList()));
	return listToFill;
    }

    /**
     * Get a list of persons from a list of firestation sort by number, thanks to
     * the address
     * 
     * @param stationNumber
     * @return a list of persons who the address is covered by the firestation
     */
    public List<PersonAdaptative> getPersonsAdaptativeByFireStationNumber(int stationNumber) {
	List<FireStation> listFireStations = fireStationService.getFirestationsByStationNumber(stationNumber)
		.getListFirestation();
	List<PersonAdaptative> listPersons = personService.getAllPersonsAdaptative().getListPersons();
	List<PersonAdaptative> listToFill = new ArrayList<PersonAdaptative>();
	listToFill.addAll(listPersons.stream()
		.filter(p -> listFireStations.stream().anyMatch(fs -> p.getAddress().equals(fs.getAddress())))
		.collect(Collectors.toList()));
	return listToFill;
    }

    /**
     * Get a list of persons from a list of station number
     * 
     * @param stations list of station number
     * @return a list of persons who the address is coverd by the firestation
     */
    public List<PersonAdaptative> getPersonsByListFireStations(int[] stations) {

	List<PersonAdaptative> listToReturn = new ArrayList<PersonAdaptative>();

	List<PersonAdaptative> listAllPersons = personService.getAllPersonsAdaptative().getListPersons();

	List<FireStation> listFireStations = fireStationService.getFireStationByListOfStationNumber(stations)
		.getListFirestation();

	for (PersonAdaptative p : listAllPersons) {
	    for (FireStation fs : listFireStations) {
		if (p.getAddress().equals(fs.getAddress())) {
		    listToReturn.add(p);
		}
	    }
	}

	return listToReturn;
    }
}
