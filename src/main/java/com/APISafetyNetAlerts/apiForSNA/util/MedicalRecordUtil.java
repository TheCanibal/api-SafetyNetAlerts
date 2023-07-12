package com.APISafetyNetAlerts.apiForSNA.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.MedicalRecordService;

/**
 * class Util to make calculation with medical records
 * 
 */
@Component
public class MedicalRecordUtil {

    @Autowired
    private MedicalRecordService medicalRecordService;

    private static Logger logger = LogManager.getLogger(MedicalRecordUtil.class);

    public LocalDate convertStringIntoDate(String date) {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	LocalDate dateToSend = LocalDate.parse(date, formatter);
	return dateToSend;
    }

    /**
     * get all the minors from the list
     * 
     * @param listPersons list of persons
     * @return number of minors in the list
     */
    public int getNumberOfMinorsPersons(List<PersonAdaptative> listPersons) {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	int personnesMineures = 0;

	List<MedicalRecords> listMedicalRecords = medicalRecordService.getMedicalRecords().getListMedicalrecords();
	for (PersonAdaptative p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (p.getFirstName().equals(mr.getFirstName()) && p.getLastName().equals(mr.getLastName())) {
		    bd = convertStringIntoDate(mr.getBirthdate());
		    ageDiff = ChronoUnit.YEARS.between(bd, ld);
		    if (ageDiff <= 18) {
			personnesMineures++;
		    }
		}
	    }
	}

	return personnesMineures;
    }

    /**
     * get all the majors from the list
     * 
     * @param listPersons list of persons
     * @return number of majors in the list
     */
    public int getNumberOfMajorsPersons(List<PersonAdaptative> listPersons) {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	int personnesMajeures = 0;

	List<MedicalRecords> listMedicalRecords = medicalRecordService.getMedicalRecords().getListMedicalrecords();
	for (PersonAdaptative p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (p.getFirstName().equals(mr.getFirstName()) && p.getLastName().equals(mr.getLastName())) {
		    bd = convertStringIntoDate(mr.getBirthdate());
		    ageDiff = ChronoUnit.YEARS.between(bd, ld);
		    if (ageDiff >= 18) {
			personnesMajeures++;
		    }
		}
	    }
	}
	return personnesMajeures;
    }

    /**
     * get a list of minors from the list
     * 
     * @param listPersons list of persons
     * @return list of minors from the param list
     */
    public List<PersonAdaptative> getListOfMinorsPersons(List<PersonAdaptative> listPersons) {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	List<PersonAdaptative> minorsList = new ArrayList<PersonAdaptative>();

	List<MedicalRecords> listMedicalRecords = medicalRecordService.getMedicalRecords().getListMedicalrecords();
	for (PersonAdaptative p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (mr.getFirstName().equals(p.getFirstName())) {
		    bd = convertStringIntoDate(mr.getBirthdate());
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

    /**
     * get a list of majors from the list
     * 
     * @param listPersons list of persons
     * @return list of majors from the param list
     */
    public List<PersonAdaptative> getListOfMajorsPersons(List<PersonAdaptative> listPersons) {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long ageDiff = 0;
	List<PersonAdaptative> majorsList = new ArrayList<PersonAdaptative>();

	List<MedicalRecords> listMedicalRecords = medicalRecordService.getMedicalRecords().getListMedicalrecords();
	for (PersonAdaptative p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (mr.getFirstName().equals(p.getFirstName())) {
		    bd = convertStringIntoDate(mr.getBirthdate());
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

    /**
     * Set the age of the persons in the list
     * 
     * @param listPersons list of persons
     * @return list of persons with their age
     */
    public List<PersonAdaptative> getListOfPersonsWithAge(List<PersonAdaptative> listPersons) {
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long age = 0;
	List<PersonAdaptative> ageList = new ArrayList<PersonAdaptative>();

	List<MedicalRecords> listMedicalRecords = medicalRecordService.getMedicalRecords().getListMedicalrecords();

	for (PersonAdaptative p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (mr.getFirstName().equals(p.getFirstName())) {
		    bd = convertStringIntoDate(mr.getBirthdate());
		    age = ChronoUnit.YEARS.between(bd, ld);
		    p.setAge(age);
		    ageList.add(p);

		}
	    }
	}

	return ageList;

    }

    /**
     * Set the medical backgrounds of the persons in the list
     * 
     * @param listPersons list of persons
     * @return list of persons with their medical backgrounds
     */
    public List<PersonAdaptative> getListPersonsWithTheirMedicalBackgrounds(List<PersonAdaptative> listPersons) {

	List<PersonAdaptative> listToReturn = new ArrayList<PersonAdaptative>();
	List<MedicalRecords> listMedicalRecord = medicalRecordService.getMedicalRecords().getListMedicalrecords();

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
