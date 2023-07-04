package com.APISafetyNetAlerts.apiForSNA.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MedicalRecords {

    private String firstName;

    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate birthdate;

    private String[] medications;

    private String[] allergies;

    public MedicalRecords() {
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
	return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
	this.birthdate = birthdate;
    }

    public String[] getMedications() {
	return medications;
    }

    public void setMedications(String[] medications) {
	this.medications = medications;
    }

    public String[] getAllergies() {
	return allergies;
    }

    public void setAllergies(String[] allergies) {
	this.allergies = allergies;
    }

}
