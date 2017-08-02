package com.github.yuri0x7c1.ofbiz.explorer.service.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Attribute;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.AutoAttributes;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceUtil {

	public static final String LOCATION_PACKAGE = "org.apache.ofbiz";
	public static final String LOCATION_COMPONENT = "component://";

	@Autowired
	private Environment env;

	@Autowired
	private OfbizInstance ofbizInstance;

	public  List<ServiceParameter> getServiceParameters(Service service) {
		List<ServiceParameter> serviceParams = new ArrayList<>();
		for (Object attr : service.getAutoAttributesOrAttribute()) {
			if (attr instanceof Attribute) {
				ServiceParameter serviceParam = ServiceParameter.builder()
					.name(((Attribute) attr).getName())
					.optional(Boolean.valueOf(((Attribute) attr).getOptional()))
					.type(((Attribute) attr).getType())
					.mode(((Attribute) attr).getMode())
					.entityName(((Attribute) attr).getEntityName())
					.build();
				serviceParams.add(serviceParam);
			}
			else if (attr instanceof AutoAttributes) {
				String entityName = ((AutoAttributes) attr).getEntityName();
				if (entityName == null) entityName = service.getDefaultEntityName();
				log.debug("entity name: {}", entityName);

				String include = ((AutoAttributes) attr).getInclude();
				Entity entity = ofbizInstance.getAllEntities().get(entityName);
				List<Field> entityFields = OfbizUtil.getFields(entity, include);
				for (Field f : entityFields) {
					ServiceParameter serviceParam = ServiceParameter.builder()
							.name(f.getName())
							.optional(Boolean.valueOf(((AutoAttributes) attr).getOptional()))
							.mode(((AutoAttributes) attr).getMode())
							.entityName(entityName)
							.build();
					serviceParams.add(serviceParam);
				}
			}
		}

		// add internal parameters
		serviceParams.add(new ServiceParameter("locale", true, "java.util.Locale", "INOUT", true, null));
		serviceParams.add(new ServiceParameter("login.password", true, "String", "IN", true, null));
		serviceParams.add(new ServiceParameter("login.username", true, "String", "IN", true, null));
		serviceParams.add(new ServiceParameter("timeZone", true, "java.util.TimeZone", "INOUT", true, null));
		serviceParams.add(new ServiceParameter("userLogin", true, "org.apache.ofbiz.entity.GenericValue", "INOUT", true, null));

		serviceParams.add(new ServiceParameter("errorMessage", true, "String", "OUT", true, null));
		serviceParams.add(new ServiceParameter("errorMessageList", true, "List", "OUT", true, null));
		serviceParams.add(new ServiceParameter("responseMessage", true, "String", "OUT", true, null));
		serviceParams.add(new ServiceParameter("successMessage", true, "String", "OUT", true, null));
		serviceParams.add(new ServiceParameter("successMessageList", true, "List", "OUT", true, null));

		return serviceParams;
	}

	public static final String SERVICE_PARAMETER_MODE_IN = "IN";
	public static final String SERVICE_PARAMETER_MODE_OUT = "OUT";
	public static final String SERVICE_PARAMETER_MODE_INOUT = "INOUT";

	public List<ServiceParameter> getServiceInParameters(Service service) {
		List<ServiceParameter> allParams = getServiceParameters(service);
		List<ServiceParameter> inParams = new ArrayList<>();
		for (ServiceParameter param : allParams) {
			if (param.getMode().equals(SERVICE_PARAMETER_MODE_IN) || param.getMode().equals(SERVICE_PARAMETER_MODE_INOUT)) {
				inParams.add(param);
			}
		}
		return inParams;
	}

	public List<ServiceParameter> getServiceOutParameters(Service service) {
		List<ServiceParameter> allParams = getServiceParameters(service);
		List<ServiceParameter> outParams = new ArrayList<>();
		for (ServiceParameter param : allParams) {
			if (param.getMode().equals(SERVICE_PARAMETER_MODE_OUT) || param.getMode().equals(SERVICE_PARAMETER_MODE_OUT)) {
				outParams.add(param);
			}
		}
		return outParams;
	}



	public String locationToPackageName(String location) {
		String basePackage = env.getProperty("generator.base_package");
		String servicePackage = basePackage;

		if (location != null) {
			if (location.startsWith(LOCATION_PACKAGE)) {
				servicePackage = basePackage + location.substring(LOCATION_PACKAGE.length(), location.lastIndexOf('.'));
			}
			else if (location.startsWith(LOCATION_COMPONENT)) {
				servicePackage = basePackage + "." + location.substring(LOCATION_COMPONENT.length(), location.lastIndexOf('/')).replace('/', '.');
			}
		}
		return servicePackage;
	}
}
