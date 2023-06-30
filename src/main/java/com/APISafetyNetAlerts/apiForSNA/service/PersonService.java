package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.repository.PersonRepository;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public ListPerson getPersons() throws IOException {
	return personRepository.findAllPersons();
    }

    public ListPerson getPersonsByAdress(String address) throws IOException {
	return personRepository.findByAddressPersons(address);
    }

    public ListPersonAdaptative getPersonsAdaptative() throws IOException {
	return personRepository.findAllPersonAdaptative();
    }

    public ListPersonAdaptative getPersonsAdaptativeByAdress(String address) throws IOException {
	return personRepository.findByAddressPersonAdaptative(address);
    }

    public List<String> getPhoneNumber(List<Person> listPersons) throws IOException {

	List<String> listPhoneNumber = new ArrayList<String>();
	for (Person p : listPersons) {
	    listPhoneNumber.add(p.getPhone());
	}
	return listPhoneNumber;
    }

}
