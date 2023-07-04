package com.APISafetyNetAlerts.apiForSNA.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListPerson {

    @JsonProperty("persons")
    List<Person> listPersons;

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
