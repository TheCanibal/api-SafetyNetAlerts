package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListPerson;
import com.APISafetyNetAlerts.apiForSNA.restModel.ListPersonAdaptative;

@Repository
public interface PersonRepository {
    public ListPerson findAllPersons() throws IOException;

    public ListPerson findByAddressPersons(String address) throws IOException;

    public ListPersonAdaptative findAllPersonAdaptative() throws IOException;

    public ListPersonAdaptative findByAddressPersonAdaptative(String address) throws IOException;
}
