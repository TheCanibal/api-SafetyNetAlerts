package com.APISafetyNetAlerts.apiForSNA.model;

public class FireStation {

    private String address;

    private String station;

    public FireStation() {
    }

    public FireStation(String address, String station) {
	this.address = address;
	this.station = station;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getStationNumber() {
	return station;
    }

    public void setStationNumber(String stationNumber) {
	this.station = stationNumber;
    }

}
