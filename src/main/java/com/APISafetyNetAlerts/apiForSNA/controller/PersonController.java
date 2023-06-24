package com.APISafetyNetAlerts.apiForSNA.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.ListPersons;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    /**
     * Read - Get all persons
     * 
     * @return - A list of persons full filled
     * @throws IOException
     */
    @GetMapping("/persons")
    public MappingJacksonValue getPersons() throws IOException {
	ListPersons listPersons = personService.getPersons();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listPersons);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all persons
     * 
     * @return - A list of persons full filled
     * @throws IOException
     */
    @GetMapping("/personsFiltre")
    public MappingJacksonValue getFilteredPersons() throws IOException {
	ListPersons listPersons = personService.getPersons();
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("phone", "zip");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listPersons);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

    /**
     * Read - Get all firestations
     * 
     * @return - A List of firestations full filled
     * @throws IOException
     */
    @GetMapping("/firestation")
    public MappingJacksonValue getPersonsCoveredByFireStation(@RequestParam int station) throws IOException {
	List<Person> listPersons = new ArrayList<Person>();
	List<Person> listPersonsCompare = personService.getPersons().getListPersons();
	List<FireStation> listFireStationCompare = fireStationService.getFireStations().getListFirestation();
	for (Person p : listPersonsCompare) {
	    for (FireStation fs : listFireStationCompare) {
		if (fs.getStation() == station) {
		    if (p.getAddress().equals(fs.getAddress())) {
			listPersons.add(p);
		    }
		}
	    }
	}
	ListPersons listPersonsToSend = new ListPersons(listPersons);
	for (Person p : listPersonsToSend.getListPersons()) {
	    System.out.println(p.getFirstName() + p.getLastName() + p.getAddress() + p.getCity() + p.getZip()
		    + p.getPhone() + p.getEmail());
	}
	SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.filterOutAllExcept("firstName", "lastName",
		"address", "phone");
	FilterProvider filtres = new SimpleFilterProvider().addFilter("filtreDynamiquePerson", monFiltre);
	MappingJacksonValue personFiltres = new MappingJacksonValue(listPersonsToSend);
	personFiltres.setFilters(filtres);
	return personFiltres;
    }

}
