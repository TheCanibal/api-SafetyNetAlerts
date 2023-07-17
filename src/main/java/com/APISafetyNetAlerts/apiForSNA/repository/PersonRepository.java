package com.APISafetyNetAlerts.apiForSNA.repository;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;

/**
 * Interface for the API to collect persons data
 */
@Repository
public interface PersonRepository {

    /**
     * Read JSON file if it has not been read already and load it
     * 
     * @return list of persons from file
     */
    public ListPerson loadPersons(boolean force);

    /**
     * Read JSON file if it has not been read already
     * 
     * @return list of person adaptative from file
     */
    public ListPersonAdaptative loadPersonsAdaptative(boolean force);

    /**
     * Get all persons
     * 
     * @return a list of all persons
     */
    public ListPerson findAllPersons();

    /**
     * Get all persons adaptative
     * 
     * @return a list of all persons adaptative
     */
    public ListPersonAdaptative findAllPersonAdaptative();

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     */
    public ListPerson findPersonsByCity(String city);

    /**
     * Get all persons who live at an address
     * 
     * @param address address of a person
     * @return a list of persons who live at an address
     */
    public ListPersonAdaptative findPersonAdaptativeByAddress(String address);

    /**
     * Get all persons with the last name
     * 
     * @param lastName last name of persons
     * @return a list of persons with the last name
     */
    public ListPersonAdaptative findPersonsAdaptativeByLastName(String lastName);

    /**
     * Add new person to the JSON File
     * 
     * @param person person to add
     * @return created person
     */
    public Person savePerson(Person person);

    /**
     * Get all persons with last name AND first name
     * 
     * @param lastName  last name of a person
     * @param firstName first name of a person
     * @return a list of persons with last name AND first name
     */
    public ListPersonAdaptative findPersonsAdaptativeByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Update person in the JSON File
     * 
     * @param person person to update
     * @return updated person
     */
    public Person updatePerson(Person person);
}
