package com.APISafetyNetAlerts.apiForSNA.model;

import java.util.List;

public class ListFireStation {

    private List<FireStation> listFirestation;

    public ListFireStation() {
    }

    public ListFireStation(List<FireStation> listFirestation) {
	this.listFirestation = listFirestation;
    }

    public List<FireStation> getListFirestation() {
	return listFirestation;
    }

    public void setListFirestation(List<FireStation> listFirestation) {
	this.listFirestation = listFirestation;
    }

}
