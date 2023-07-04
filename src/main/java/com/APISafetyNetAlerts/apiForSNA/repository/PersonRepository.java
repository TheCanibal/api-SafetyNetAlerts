package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

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
     * @throws IOException
     */
    public ListPerson findAllPersons() throws IOException;

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     * @throws IOException
     */
    public ListPerson findPersonsByCity(String city) throws IOException;

    /**
     * Get all persons who live at an address
     * 
     * @param address address of a person
     * @return a list of persons who live at an address
     * @throws IOException
     */
    public ListPersonAdaptative findAllPersonAdaptative() throws IOException;

    /**
     * Get all persons who live at an address
     * 
     * @param address address of a person
     * @return a list of persons who live at an address
     * @throws IOException
     */
    public ListPersonAdaptative findPersonAdaptativeByAddress(String address) throws IOException;

    /**
     * Get all persons who live in the city
     * 
     * @param city city where lives the person
     * @return a list of persons who lives in the city
     * @throws IOException
     */
    public ListPersonAdaptative findPersonsByLastName(String lastName) throws IOException;

}
