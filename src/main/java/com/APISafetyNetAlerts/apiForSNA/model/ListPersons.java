package com.APISafetyNetAlerts.apiForSNA.model;

import java.util.List;

public class ListPersons {

    private List<Person> listPersons;

    public ListPersons() {
    }

    public ListPersons(List<Person> listPersons) {
	this.listPersons = listPersons;
    }

    public List<Person> getListPersons() {
	return listPersons;
    }

    public void setListPersons(List<Person> listPersons) {
	this.listPersons = listPersons;
    }

}
