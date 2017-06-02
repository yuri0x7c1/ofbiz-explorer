package com.github.yuri0x7c1.ofbiz.explorer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		OfbizUtil.readOfbizComponentGroupDirectories();
	}
}
