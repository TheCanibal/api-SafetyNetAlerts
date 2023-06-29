package com.APISafetyNetAlerts.apiForSNA.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APISafetyNetAlerts.apiForSNA.model.ListMedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.repository.MedicalRecordsRepository;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordsRepository medicalRecordRepository;

    public ListMedicalRecords getMedicalRecords() throws IOException {
	return medicalRecordRepository.findAll();
    }

    public ListMedicalRecords getMedicalRecordsByLastName(String lastName) throws IOException {
	return medicalRecordRepository.findByLastName(lastName);
    }

    public int getNumberOfMinorsPersons(List<Person> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	int personnesMineures = 0;

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();
	for (Person p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (p.getFirstName().equals(mr.getFirstName()) && p.getLastName().equals(mr.getLastName())) {
		    bd = mr.getBirthdate();
		    ageDiff = ChronoUnit.YEARS.between(bd, ld);
		    if (ageDiff <= 18) {
			personnesMineures++;
		    }
		}
	    }
	}

	return personnesMineures;
    }

    public int getNumberOfMajorsPersons(List<Person> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	int personnesMajeures = 0;

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();
	for (Person p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (p.getFirstName().equals(mr.getFirstName()) && p.getLastName().equals(mr.getLastName())) {
		    bd = mr.getBirthdate();
		    ageDiff = ChronoUnit.YEARS.between(bd, ld);
		    if (ageDiff >= 18) {
			personnesMajeures++;
		    }
		}
	    }
	}

	return personnesMajeures;
    }

    public List<Person> getListOfMinorsPersons(List<Person> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	List<Person> minorsList = new ArrayList<Person>();

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();
	for (Person p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (mr.getFirstName().equals(p.getFirstName())) {
		    bd = mr.getBirthdate();
		    ageDiff = ChronoUnit.YEARS.between(bd, ld);
		    if (ageDiff <= 18) {
			p.setAge(ageDiff);
			minorsList.add(p);
		    }
		}
	    }
	}

	return minorsList;
    }

    public List<Person> getListOfMajorsPersons(List<Person> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	List<Person> majorsList = new ArrayList<Person>();

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();
	for (Person p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (mr.getFirstName().equals(p.getFirstName())) {
		    bd = mr.getBirthdate();
		    ageDiff = ChronoUnit.YEARS.between(bd, ld);
		    if (ageDiff >= 18) {
			p.setAge(ageDiff);
			majorsList.add(p);
		    }
		}
	    }
	}

	return majorsList;

    }

}
