package com.APISafetyNetAlerts.apiForSNA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
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

    /**
     * Get all persons
     * 
     * @return a list of all persons
     */
    public ListPerson getAllPersons() {
	return personRepository.findAllPersons();
    }

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     */
    public ListPerson getPersonsByCity(String city) {
	return personRepository.findPersonsByCity(city);
    }

    /**
     * Get all persons
     * 
     * @return a list of all persons
     */
    public ListPersonAdaptative getAllPersonsAdaptative() {
	return personRepository.findAllPersonAdaptative();
    }

    /**
     * Get all persons who live at an address
     * 
     * @param address address of a person
     * @return a list of persons who live at an address
     */
    public ListPersonAdaptative getPersonsAdaptativeByAdress(String address) {
	return personRepository.findPersonAdaptativeByAddress(address);
    }

    /**
     * Get all persons with the last name
     * 
     * @param lastName last name of persons
     * @return a list of persons with the last name
     */
    public ListPersonAdaptative getPersonsAdaptativeByLastName(String lastName) {
	return personRepository.findPersonsAdaptativeByLastName(lastName);
    }

    /**
     * Get all persons with last name AND first name
     * 
     * @param lastName  last name of a person
     * @param firstName first name of a person
     * @return a list of persons with last name AND first name
     */
    public ListPersonAdaptative getPersonsAdaptativeByFirstNameAndLastName(String firstName, String lastName) {
	return personRepository.findPersonsAdaptativeByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Add new person to the JSON File
     * 
     * @param person person to add
     * @return created person
     */
    public void createPerson(Person person) {
	personRepository.savePerson(person);
    }

    /**
     * Update person in the JSON File
     * 
     * @param person person to update
     * @return updated person
     */
    public void updatePerson(Person person) {
	personRepository.updatePerson(person);
    }

    /**
     * Delete person in the JSON file
     * 
     * @param person person to delete
     * @return person deleted
     */
    public void deletePerson(Person person) {
	personRepository.deletePerson(person);
    }

}
