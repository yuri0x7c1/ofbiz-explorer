package com.github.yuri0x7c1.ofbiz.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.core.env.Environment;

import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Application implements CommandLineRunner {
	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(env.getProperty("generator.destination_path"));

		/*
		ServiceGenerator generator = new ServiceGenerator();
		generator.setOfbizInstance(ofbizInstance);
		generator.setService(ofbizInstance.getAllServices().get("findPartiesById"));
		generator.setDestinationPath(env.getProperty("generator.destination_path"));

		log.info(generator.generate());
		*/
	}
}
