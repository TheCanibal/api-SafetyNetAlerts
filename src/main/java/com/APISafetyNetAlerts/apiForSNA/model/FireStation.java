package com.APISafetyNetAlerts.apiForSNA.model;

public class FireStation {

    private String address;

    private int station;

    public FireStation() {
    }

    public FireStation(String address, int station) {
	super();
	this.address = address;
	this.station = station;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public int getStation() {
	return station;
    }

    public void setStation(int station) {
	this.station = station;
    }

}
