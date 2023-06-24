package com.APISafetyNetAlerts.apiForSNA.model;

import java.util.List;

public class ListMedicalRecords {

    private List<MedicalRecords> listMedicalrecords;

    public ListMedicalRecords() {
    }

    public ListMedicalRecords(List<MedicalRecords> listMedicalrecords) {
	this.listMedicalrecords = listMedicalrecords;
    }

    public List<MedicalRecords> getListMedicalrecords() {
	return listMedicalrecords;
    }

    public void setListMedicalrecords(List<MedicalRecords> listMedicalrecords) {
	this.listMedicalrecords = listMedicalrecords;
    }

}
