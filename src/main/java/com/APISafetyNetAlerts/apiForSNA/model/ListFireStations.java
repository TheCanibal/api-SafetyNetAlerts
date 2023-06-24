package com.APISafetyNetAlerts.apiForSNA.model;

import java.util.List;

public class ListFireStations {

    private List<FireStation> listFirestation;

    public ListFireStations() {
    }

    public ListFireStations(List<FireStation> listFirestation) {
	this.listFirestation = listFirestation;
    }

    public List<FireStation> getListFirestation() {
	return listFirestation;
    }

    public void setListFirestation(List<FireStation> listFirestation) {
	this.listFirestation = listFirestation;
    }

}
