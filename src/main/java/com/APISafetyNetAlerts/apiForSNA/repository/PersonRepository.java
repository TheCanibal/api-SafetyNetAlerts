package com.APISafetyNetAlerts.apiForSNA.repository;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;

/**
 * Interface for the API to collect persons data
 */
@Repository
public interface PersonRepository {

    /**
     * Get all persons
     * 
     * @return a list of all persons
     */
    public ListPerson findAllPersons();

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
    public ListPersonAdaptative findAllPersonAdaptative();

    /**
     * Get all persons who live at an address
     * 
     * @param address address of a person
     * @return a list of persons who live at an address
     */
    public ListPersonAdaptative findPersonAdaptativeByAddress(String address);

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     */
    public ListPersonAdaptative findPersonsAdaptativeByLastName(String lastName);

}
