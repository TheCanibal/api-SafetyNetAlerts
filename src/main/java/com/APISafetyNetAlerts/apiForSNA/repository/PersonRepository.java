package com.APISafetyNetAlerts.apiForSNA.repository;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.APISafetyNetAlerts.apiForSNA.model.ListPersons;

@Repository
public interface PersonRepository {
    ListPersons findAll() throws IOException;
}
