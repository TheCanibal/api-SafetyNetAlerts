package com.APISafetyNetAlerts.apiForSNA.util;

import java.util.ArrayList;
import java.util.List;

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
	List<String> allPersonsAddress = new ArrayList<String>();
	List<Person> listAllPersons = personService.getAllPersons().getListPersons();
	if (listAllPersons != null) {
	    for (Person p : listAllPersons) {
		allPersonsAddress.add(p.getAddress());
	    }
	}
	return allPersonsAddress;
    }

    /**
     * Get all first name from persons
     * 
     * @param listPersons list of persons
     * @return a list with all the first name
     */
    public List<String> getFirstNameFromListPersons() {
	List<String> allPersonsFirstName = new ArrayList<String>();
	List<Person> listAllPersons = personService.getAllPersons().getListPersons();
	if (listAllPersons != null) {
	    for (Person p : listAllPersons) {
		allPersonsFirstName.add(p.getFirstName());
	    }
	}
	return allPersonsFirstName;
    }

    /**
     * Get all last name from persons
     * 
     * @param listPersons list of persons
     * @return a list with all the last name
     */
    public List<String> getLastNameFromListPersons() {
	List<String> allPersonsLastName = new ArrayList<String>();
	List<Person> listAllPersons = personService.getAllPersons().getListPersons();
	if (listAllPersons != null) {
	    for (Person p : listAllPersons) {
		allPersonsLastName.add(p.getLastName());
	    }
	}
	return allPersonsLastName;
    }

    /**
     * Get all cities from persons
     * 
     * @param listPersons list of persons
     * @return a list with all the cities
     */
    public List<String> getCityFromListPersons() {
	List<String> allPersonsCities = new ArrayList<String>();
	List<Person> listAllPersons = personService.getAllPersons().getListPersons();
	if (listAllPersons != null) {
	    for (Person p : listAllPersons) {
		allPersonsCities.add(p.getCity());
	    }
	}
	return allPersonsCities;
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
