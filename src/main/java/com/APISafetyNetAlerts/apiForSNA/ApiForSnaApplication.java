package com.APISafetyNetAlerts.apiForSNA;

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
	List<Person> lp = personService.getPersons().getListPersons();
	System.out.println(mrs.getNumberOfMinorsPersons(lp));
	System.out.println(mrs.getNumberOfMajorsPersons(lp));

    }

}
