package com.APISafetyNetAlerts.apiForSNA;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.APISafetyNetAlerts.apiForSNA.controller.FireStationController;
import com.APISafetyNetAlerts.apiForSNA.controller.MedicalRecordController;
import com.APISafetyNetAlerts.apiForSNA.controller.PersonController;
import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
import com.APISafetyNetAlerts.apiForSNA.service.MedicalRecordService;
import com.APISafetyNetAlerts.apiForSNA.service.PersonService;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
	DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class ApiForSnaApplication implements CommandLineRunner {

    @Autowired
    PersonController personController;

    @Autowired
    MedicalRecordController mrc;

    @Autowired
    MedicalRecordService mrs;

    @Autowired
    FireStationController fsc;

    @Autowired
    PersonService personService;

    @Autowired
    FireStationService fireStationService;

    public static void main(String[] args) {
	SpringApplication.run(ApiForSnaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

	List<MedicalRecords> lmr = mrs.getMedicalRecords().getListMedicalrecords();
	LocalDate ld = LocalDate.now();
	LocalDate bd;
	long diff = 0;

	for (MedicalRecords mr : lmr) {
	    bd = mr.getBirthdate();
	    diff = ChronoUnit.DAYS.between(bd, ld);
	    System.out.println(bd + " La différence est de : " + diff + " jours");
	}

	List<Person> listPersons = new ArrayList<Person>();
	List<Person> listPersonsCompare = personService.getPersons().getListPersons();
	List<FireStation> listFireStationCompare = fireStationService.getFireStations().getListFirestation();
	List<MedicalRecords> listMedicalRecords = mrs.getMedicalRecords().getListMedicalrecords();
	int personnesMineurs = 0;
	int personnesMajeurs = 0;
	for (Person p : listPersonsCompare) {
	    for (FireStation fs : listFireStationCompare) {
		if (fs.getStation() == 1) {
		    if (p.getAddress().equals(fs.getAddress())) {
			listPersons.add(p);
			System.out.println("Prénom : " + p.getFirstName() + " Nom : " + p.getLastName());
		    }
		}
	    }
	}
	for (Person p : listPersons) {
	    for (MedicalRecords mr : listMedicalRecords) {
		if (p.getFirstName().equals(mr.getFirstName()) && p.getLastName().equals(mr.getLastName())) {
		    bd = mr.getBirthdate();
		    diff = ChronoUnit.DAYS.between(bd, ld);
		    if (diff <= 6570) {
			personnesMineurs++;
		    } else {
			personnesMajeurs++;
		    }
		}
	    }
	}
	System.out.println("Nombre de personne(s) mineur(s) : " + personnesMineurs);
	System.out.println("Nombre de personne(s) majeur(s) : " + personnesMajeurs);
    }

}
