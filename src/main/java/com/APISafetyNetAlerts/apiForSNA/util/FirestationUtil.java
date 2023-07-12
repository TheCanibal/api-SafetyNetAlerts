package com.APISafetyNetAlerts.apiForSNA.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.plexus.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;

/**
 * class Util to make calculation with firestation
 *
 */
@Component
public class FirestationUtil {

    @Autowired
    private FireStationService fireStationService;

    /**
     * Get firestations number that cover the address of the persons in the list
     * 
     * @param listPersons list of persons
     * @return list of persons with their firestations number that cover them
     */
    public List<PersonAdaptative> getStationNumberByListPersons(List<PersonAdaptative> listPersons) {

	List<PersonAdaptative> listToReturn = new ArrayList<PersonAdaptative>();

	List<FireStation> listFireStation = fireStationService.getAllFireStations().getListFirestation();

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

    public List<Integer> getAllStationNumber() {
	Set<Integer> stationNumbers = new HashSet<Integer>();
	List<FireStation> listFireStation = fireStationService.getAllFireStations().getListFirestation();
	for (FireStation fs : listFireStation) {
	    stationNumbers.add(fs.getStation());
	}
	List<Integer> listToSend = new ArrayList<Integer>();
	listToSend.addAll(stationNumbers);
	return listToSend;
    }

    public List<Integer> compareElementsOfTwoListAndSendListWithSameElements(List<Integer> listOne,
	    List<Integer> listTwo) {
	List<Integer> listToSend = new ArrayList<Integer>();
	listToSend = (List<Integer>) CollectionUtils.intersection(listOne, listTwo);
	return listToSend;
    }

}
