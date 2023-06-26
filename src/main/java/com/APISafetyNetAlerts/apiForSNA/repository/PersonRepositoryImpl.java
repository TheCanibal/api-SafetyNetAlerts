package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListPersons;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    ObjectMapper mapper = new ObjectMapper();
    List<Person> listPersonsToFill;

    public ListPersons findAll() throws IOException {
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPersons listPersons = mapper.readValue(file, ListPersons.class);
	return listPersons;
    }

    public ListPersons findByAddress(String address) throws IOException {
	listPersonsToFill = new ArrayList<Person>();
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	File file = new File("D:\\workspace\\git\\apiForSNA\\src\\main\\resources\\data.json");
	ListPersons listPersons = mapper.readValue(file, ListPersons.class);
	for (Person p : listPersons.getListPersons()) {
	    if (p.getAddress().equals(address)) {
		listPersonsToFill.add(p);
	    }
	}
	listPersons.setListPersons(listPersonsToFill);
	return listPersons;
    }

}
