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
import com.APISafetyNetAlerts.apiForSNA.restModel.PersonAdaptative;
import com.APISafetyNetAlerts.apiForSNA.service.FireStationService;
import com.APISafetyNetAlerts.apiForSNA.service.FirestationPersonService;
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
    PersonService ps;

    @Autowired
    FireStationService fss;

    @Autowired
    FirestationPersonService fps;

    public static void main(String[] args) {
	SpringApplication.run(ApiForSnaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

	int[] stations = { 1, -6 };
	List<PersonAdaptative> lpa = fps.getPersonsByListFireStations(stations);
	for (PersonAdaptative lpaa : lpa) {
	    System.out.println(lpaa.getFirstName());
	}

    }

}
