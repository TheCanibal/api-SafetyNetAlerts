package com.APISafetyNetAlerts.apiForSNA.restModel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListPersonAdaptative {

    @JsonProperty("persons")
    List<PersonAdaptative> listPersons;

    int personnesMajeures;

    int personnesMineures;

    public List<PersonAdaptative> getListPersons() {
	return listPersons;
    }

    public void setListPersons(List<PersonAdaptative> listPersons) {
	this.listPersons = listPersons;
    }

    public int getPersonnesMajeures() {
	return personnesMajeures;
    }

    public void setPersonnesMajeures(int personnesMajeures) {
	this.personnesMajeures = personnesMajeures;
    }

    public int getPersonnesMineures() {
	return personnesMineures;
    }

    public void setPersonnesMineures(int personnesMineures) {
	this.personnesMineures = personnesMineures;
    }

}
