package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Interaction with the object persons in the data.json file
 *
 */

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    ObjectMapper mapper = new ObjectMapper();
    List<Person> listPersonsToFill;
    List<PersonAdaptative> listPersonsAdaptativeToFill;

    /**
     * Get all persons
     * 
     * @return a list of all persons
     * @throws IOException
     */
    @Override
    public ListPerson findAllPersons() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPerson listPersons = mapper.readValue(file, ListPerson.class);
	return listPersons;
    }

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     * @throws IOException
     */
    @Override
    public ListPerson findPersonsByCity(String city) throws IOException {
	listPersonsToFill = new ArrayList<Person>();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPerson listPersons = mapper.readValue(file, ListPerson.class);
	for (Person p : listPersons.getListPersons()) {
	    if (p.getCity().equals(city)) {
		listPersonsToFill.add(p);
	    }
	}
	return listPersons;
    }

    /**
     * Get all persons
     * 
     * @return a list of all persons
     * @throws IOException
     */
    @Override
    public ListPersonAdaptative findAllPersonAdaptative() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPersonAdaptative listPersons = mapper.readValue(file, ListPersonAdaptative.class);
	return listPersons;
    }

    /**
     * Get all persons who live at an address
     * 
     * @param address address of a person
     * @return a list of persons who live at an address
     * @throws IOException
     */
    @Override
    public ListPersonAdaptative findPersonAdaptativeByAddress(String address) throws IOException {
	listPersonsAdaptativeToFill = new ArrayList<PersonAdaptative>();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPersonAdaptative listPersons = mapper.readValue(file, ListPersonAdaptative.class);
	for (PersonAdaptative p : listPersons.getListPersons()) {
	    if (p.getAddress().equals(address)) {
		listPersonsAdaptativeToFill.add(p);
	    }
	}
	listPersons.setListPersons(listPersonsAdaptativeToFill);
	return listPersons;
    }

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     * @throws IOException
     */
    @Override
    public ListPersonAdaptative findPersonsByLastName(String lastName) throws IOException {
	listPersonsAdaptativeToFill = new ArrayList<PersonAdaptative>();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPersonAdaptative listPersons = mapper.readValue(file, ListPersonAdaptative.class);
	for (PersonAdaptative p : listPersons.getListPersons()) {
	    if (p.getLastName().equals(lastName)) {
		listPersonsAdaptativeToFill.add(p);
	    }
	}
	listPersons.setListPersons(listPersonsAdaptativeToFill);
	return listPersons;
    }

}
