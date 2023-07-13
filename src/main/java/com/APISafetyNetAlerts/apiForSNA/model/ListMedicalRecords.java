package com.APISafetyNetAlerts.apiForSNA.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model represents all the object medicalrecords in the JSON file
 *
 */
public class ListMedicalRecords {

    @JsonProperty("medicalrecords")
    private List<MedicalRecords> listMedicalrecords;

    public ListMedicalRecords() {
    }

    public List<MedicalRecords> getListMedicalrecords() {
	return listMedicalrecords;
    }

    public void setListMedicalrecords(List<MedicalRecords> listMedicalrecords) {
	this.listMedicalrecords = listMedicalrecords;
    }

}
