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

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    ObjectMapper mapper = new ObjectMapper();
    List<Person> listPersonsToFill;
    List<PersonAdaptative> listPersonsAdaptativeToFill;

    @Override
    public ListPerson findAllPersons() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPerson listPersons = mapper.readValue(file, ListPerson.class);
	return listPersons;
    }

    @Override
    public ListPerson findByAddressPersons(String address) throws IOException {
	listPersonsToFill = new ArrayList<Person>();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPerson listPersons = mapper.readValue(file, ListPerson.class);
	for (Person p : listPersons.getListPersons()) {
	    if (p.getAddress().equals(address)) {
		listPersonsToFill.add(p);
	    }
	}
	listPersons.setListPersons(listPersonsToFill);
	return listPersons;
    }

    @Override
    public ListPersonAdaptative findAllPersonAdaptative() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPersonAdaptative listPersons = mapper.readValue(file, ListPersonAdaptative.class);
	return listPersons;
    }

    @Override
    public ListPersonAdaptative findByAddressPersonAdaptative(String address) throws IOException {
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

}
