package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.repository.PersonRepository;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;

/**
 * 
 * Service person that take data from repository to send it to the controller
 *
 */

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public ListPerson getPersonsByCity(String city) throws IOException {
	return personRepository.findPersonsByCity(city);
    }

    public ListPersonAdaptative getPersonsAdaptative() throws IOException {
	return personRepository.findAllPersonAdaptative();
    }

    public ListPersonAdaptative getPersonsAdaptativeByAdress(String address) throws IOException {
	return personRepository.findPersonAdaptativeByAddress(address);
    }

    public ListPersonAdaptative getPersonsAdaptativeByLastName(String lastName) throws IOException {
	return personRepository.findPersonsByLastName(lastName);
    }

}
