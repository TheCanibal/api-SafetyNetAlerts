package com.APISafetyNetAlerts.apiForSNA.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model represents all the object persons in the JSON file
 *
 */

public class ListPerson {

    @JsonProperty("persons")
    private List<Person> listPersons;

    public ListPerson() {
	super();
    }

    public List<Person> getListPersons() {
	return listPersons;
    }

    public void setListPersons(List<Person> listPersons) {
	this.listPersons = listPersons;
    }

}
