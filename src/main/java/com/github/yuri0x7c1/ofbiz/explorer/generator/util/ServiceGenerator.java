package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.util.List;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.util.StringUtils;

import com.github.yuri0x7c1.ofbiz.explorer.service.util.ServiceParameter;
import com.github.yuri0x7c1.ofbiz.explorer.service.util.ServiceUtil;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.Getter;
import lombok.Lombok;
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

		JavaClassSource serviceSource = Roaster.create(JavaClassSource.class)
			.setName(StringUtils.capitalize(service.getName()) + "Service");

		List<ServiceParameter> inParams = ServiceUtil.getServiceInParameters(service, ofbizInstance);
		List<ServiceParameter> outParams = ServiceUtil.getServiceOutParameters(service, ofbizInstance);

		// process service IN params
		if (!inParams.isEmpty()) {
			// crate In nested type
			JavaClassSource inTypeSource = Roaster.create(JavaClassSource.class).setName("In");

			// create In type fields
			for (ServiceParameter param : inParams) {
				FieldSource<JavaClassSource> fieldSource = inTypeSource.addField()
					.setName(param.getName())
					.setType(ServiceUtil.getParameterType(param))
					.setPrivate();

				fieldSource.addAnnotation(Getter.class);
				fieldSource.addAnnotation(Setter.class);
			}

			// add In nested type to service class
			serviceSource.addNestedType(inTypeSource)
				.setPublic()
				.setStatic(true)
				.setFinal(true);

			// add "in" field
			FieldSource<JavaClassSource> inFieldSource = serviceSource.addField()
				.setName("in")
				.setType(inTypeSource)
				.setPrivate();

			inFieldSource.addAnnotation(Getter.class);
			inFieldSource.addAnnotation(Setter.class);
		}

		// process service OUT params
		if (!outParams.isEmpty()) {
			// crate Out nested type
			JavaClassSource outTypeSource = Roaster.create(JavaClassSource.class).setName("Out");

			// create Out type fields
			for (ServiceParameter param : outParams) {
				FieldSource<JavaClassSource> fieldSource = outTypeSource.addField()
					.setName(param.getName())
					.setType(ServiceUtil.getParameterType(param))
					.setPrivate();

				fieldSource.addAnnotation(Getter.class);
				fieldSource.addAnnotation(Setter.class);
			}

			// add Out nested type to service class
			serviceSource.addNestedType(outTypeSource)
				.setPublic()
				.setStatic(true)
				.setFinal(true);

			// add "out" field
			FieldSource<JavaClassSource> outFieldSource = serviceSource.addField()
				.setName("out")
				.setType(outTypeSource)
				.setPrivate();

			outFieldSource.addAnnotation(Getter.class);
			outFieldSource.addAnnotation(Setter.class);
		}

		return serviceSource.toString();
	}
}
