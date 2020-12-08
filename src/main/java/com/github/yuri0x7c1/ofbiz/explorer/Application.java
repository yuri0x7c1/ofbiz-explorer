package com.github.yuri0x7c1.ofbiz.explorer;

import java.io.File;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.core.env.Environment;

import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Attribute;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
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

	private  void listServiceParameterTypes() throws Exception {
		TreeSet<String> attributeTypes = new TreeSet<>();
		for (Service service : ofbizInstance.getAllServices().values()) {
			for (Object attribute : service.getAutoAttributesOrAttribute()) {
				if (attribute instanceof Attribute) {
					attributeTypes.add(((Attribute) attribute).getType());
				}
			}
		}

		log.info("Service attribute types:\n {}", attributeTypes);
		File file = new File("service_attribute_types.txt");
		FileUtils.writeLines(file, attributeTypes);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(env.getProperty("generator.destination_path"));
	}
}
