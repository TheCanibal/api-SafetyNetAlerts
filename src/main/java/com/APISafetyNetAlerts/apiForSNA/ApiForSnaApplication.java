package com.APISafetyNetAlerts.apiForSNA;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.APISafetyNetAlerts.apiForSNA.controller.PersonController;
import com.APISafetyNetAlerts.apiForSNA.model.ListPersons;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
	DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class ApiForSnaApplication implements CommandLineRunner {

    @Autowired
    PersonController personController;

    public static void main(String[] args) {
	SpringApplication.run(ApiForSnaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
	File file = new File("src\\main\\resources\\data.json");
	System.out.println(file.getPath());
	System.out.println(file.getAbsolutePath());
	ListPersons person = personController.getPersons();
	System.out.println(person);
    }

}
