package com.APISafetyNetAlerts.apiForSNA.restModel;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("filtreDynamiquePerson")

public class PersonAdaptative {

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private int zip;

    private String phone;

    private String email;

    private long age;

    private int firestationNumber;

    private String[] medications;

    private String[] allergies;

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

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public int getZip() {
	return zip;
    }

    public void setZip(int zip) {
	this.zip = zip;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public long getAge() {
	return age;
    }

    public void setAge(long age) {
	this.age = age;
    }

    public int getFirestationNumber() {
	return firestationNumber;
    }

    public void setFirestationNumber(int firestationNumber) {
	this.firestationNumber = firestationNumber;
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
