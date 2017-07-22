package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.Getter;
import lombok.Setter;

public class ServiceGenerator {
	@Getter
	@Setter
	private OfbizInstance ofbizInstance;

	@Getter
	@Setter
	private Service service;

	public String generate() throws Exception {
		if (service == null ) throw new Exception("Service must not be null");

		JavaClassSource serviceSource = Roaster.create(JavaClassSource.class);



		return null;
	}
}
