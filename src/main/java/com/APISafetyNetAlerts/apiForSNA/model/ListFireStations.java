package com.APISafetyNetAlerts.apiForSNA.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to get all the object firestations in the JSON file
 *
 */
public class ListFireStations {

    @JsonProperty("firestations")
    private List<FireStation> listFirestation;

    public ListFireStations() {
    }

    public List<FireStation> getListFirestation() {
	return listFirestation;
    }

    public void setListFirestation(List<FireStation> listFirestation) {
	this.listFirestation = listFirestation;
    }

}
