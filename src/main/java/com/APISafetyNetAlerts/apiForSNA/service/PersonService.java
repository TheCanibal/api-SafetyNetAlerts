package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.ListPersons;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public ListPersons getPersons() throws IOException {
	return personRepository.findAll();
    }

    public ListPersons getPersonsByAdress(String address) throws IOException {
	return personRepository.findByAddress(address);
    }

    public List<String> getPhoneNumber(List<Person> listPersons) throws IOException {

	List<String> listPhoneNumber = new ArrayList<String>();
	for (Person p : listPersons) {
	    listPhoneNumber.add(p.getPhone());
	}
	return listPhoneNumber;
    }

}
