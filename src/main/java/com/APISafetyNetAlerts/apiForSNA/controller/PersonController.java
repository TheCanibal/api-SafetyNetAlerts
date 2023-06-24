package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.ListPersons;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    /**
     * Read - Get all persons
     * 
     * @return - A list of persons full filled
     * @throws IOException
     */
    @GetMapping("/persons")
    public ListPersons getPersons() throws IOException {
	return personService.getPersons();
    }

}
