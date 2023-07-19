package com.APISafetyNetAlerts.apiForSNA.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;

/**
 * class Util to make calculation with Persons
 *
 */
@Component
public class PersonUtil {

    @Autowired
    private PersonService personService;

    /**
     * Get all addresses from persons
     * 
     * @param listPersons list of persons
     * @return a list with all the addresses
     */
    public List<String> getAddressFromListPersons() {
	Set<String> allPersonsAddressSet = new HashSet<String>();
	List<Person> listAllPersons = personService.getAllPersons().getListPersons();
	List<String> allPersonsAddressList = new ArrayList<String>();
	if (listAllPersons != null) {
	    for (Person p : listAllPersons) {
		allPersonsAddressSet.add(p.getAddress());
	    }
	}
	allPersonsAddressList.addAll(allPersonsAddressSet);
	return allPersonsAddressList;
    }

    /**
     * Get all cities from persons
     * 
     * @param listPersons list of persons
     * @return a list with all the cities
     */
    public List<String> getCityFromListPersons() {
	Set<String> allPersonsCitiesSet = new HashSet<String>();
	List<Person> listAllPersons = personService.getAllPersons().getListPersons();
	List<String> allPersonsCitiesList = new ArrayList<String>();
	if (listAllPersons != null) {
	    for (Person p : listAllPersons) {
		allPersonsCitiesSet.add(p.getCity());
	    }
	}

	allPersonsCitiesList.addAll(allPersonsCitiesSet);
	return allPersonsCitiesList;
    }

    /**
     * get a person or persons with first name and last name and all other persons
     * with the same last name
     * 
     * @param firstName first name of a person
     * @param lastName  last name of a persn
     * @return list of persons
     */
    public List<PersonAdaptative> getPersonWithFirstNameAndLastNameAndOthersPersonsWithSameLastName(String firstName,
	    String lastName) {
	// list of persons with first name AND last name
	List<PersonAdaptative> listPersonsByFirstNameAndLastName = personService
		.getPersonsAdaptativeByFirstNameAndLastName(firstName, lastName).getListPersons();

	// list of person with the same last name in param
	List<PersonAdaptative> personsWithSameLastName = new ArrayList<PersonAdaptative>();

	// List to return
	List<PersonAdaptative> listToSend = new ArrayList<PersonAdaptative>();

	if (!listPersonsByFirstNameAndLastName.isEmpty()) {
	    personsWithSameLastName = personService.getPersonsAdaptativeByLastName(lastName).getListPersons();
	    // For each person, add to the list persons with the first name or the last name
	    // argument
	    for (PersonAdaptative p : listPersonsByFirstNameAndLastName) {
		for (PersonAdaptative p2 : personsWithSameLastName) {
		    if (p.getFirstName().equals(firstName) || p.getLastName().equals(lastName)) {
			listToSend.add(p2);
		    }
		}
	    }
	}
	return listToSend;
    }
}
