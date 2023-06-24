package com.APISafetyNetAlerts.apiForSNA.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("filtreDynamique")

public class FireStation {

    @JsonProperty("address")
    private String address;

    @JsonProperty("station")
    private int station;

    public FireStation() {
    }

    public FireStation(String address, int station) {
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
