package com.APISafetyNetAlerts.apiForSNA.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.APISafetyNetAlerts.apiForSNA.model.Person;
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
}
