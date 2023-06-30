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
import com.APISafetyNetAlerts.apiForSNA.repository.MedicalRecordsRepository;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;

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

    public int getNumberOfMinorsPersons(List<PersonAdaptative> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	int personnesMineures = 0;

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();
	for (PersonAdaptative p : listPersons) {
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

    public int getNumberOfMajorsPersons(List<PersonAdaptative> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	int personnesMajeures = 0;

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();
	for (PersonAdaptative p : listPersons) {
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

    public List<PersonAdaptative> getListOfMinorsPersons(List<PersonAdaptative> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	List<PersonAdaptative> minorsList = new ArrayList<PersonAdaptative>();

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();
	for (PersonAdaptative p : listPersons) {
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

    public List<PersonAdaptative> getListOfMajorsPersons(List<PersonAdaptative> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	List<PersonAdaptative> majorsList = new ArrayList<PersonAdaptative>();

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();
	for (PersonAdaptative p : listPersons) {
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

    public List<PersonAdaptative> getListOfPersonsWithAge(List<PersonAdaptative> listPersons) throws IOException {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long age = 0;
	List<PersonAdaptative> ageList = new ArrayList<PersonAdaptative>();

	List<MedicalRecords> listMedicalRecords = medicalRecordRepository.findAll().getListMedicalrecords();

	for (PersonAdaptative p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (mr.getFirstName().equals(p.getFirstName())) {
		    bd = mr.getBirthdate();
		    age = ChronoUnit.YEARS.between(bd, ld);
		    p.setAge(age);
		    ageList.add(p);

		}
	    }
	}

	return ageList;

    }

    public List<PersonAdaptative> getListPersonsWithTheirMedicalBackgrounds(List<PersonAdaptative> listPersons)
	    throws IOException {

	List<PersonAdaptative> listToReturn = new ArrayList<PersonAdaptative>();
	List<MedicalRecords> listMedicalRecord = medicalRecordRepository.findAll().getListMedicalrecords();

	for (PersonAdaptative p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecord) {
		if (p.getFirstName().equals(mr.getFirstName()) && p.getLastName().equals(mr.getLastName())) {
		    p.setMedications(mr.getMedications());
		    p.setAllergies(mr.getAllergies());
		    listToReturn.add(p);
		}
	    }
	}

	return listToReturn;

    }

}
