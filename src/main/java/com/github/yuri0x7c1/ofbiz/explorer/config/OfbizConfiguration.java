package com.github.yuri0x7c1.ofbiz.explorer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;

@Configuration
public class OfbizConfiguration {

	@Value("${generator.destination_path ?: '/tmp/ofbiz/'}")
	public String destinationPath;

	@Value("${generator.base_package ?: 'org.apache.ofbiz'}")
	public String basePackage;
	
	@Value("${generator.entity.package")
	public String entityPackage;

	@Bean
	public OfbizInstance ofbizInstance() {
		return OfbizUtil.readInstance();
	}
}
