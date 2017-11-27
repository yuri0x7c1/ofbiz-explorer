package com.github.yuri0x7c1.ofbiz.explorer.service.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.FieldType;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Attribute;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.AutoAttributes;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Exclude;
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

	public  Map<String, ServiceParameter> getServiceParameters(Service service) {
		Map<String, ServiceParameter> serviceParams = new LinkedHashMap<>();
		for (Object attr : service.getAutoAttributesOrAttribute()) {
			if (attr instanceof Attribute) {
				ServiceParameter serviceParam = ServiceParameter.builder()
					.name(((Attribute) attr).getName())
					.optional(Boolean.valueOf(((Attribute) attr).getOptional()))
					.type(((Attribute) attr).getType())
					.mode(((Attribute) attr).getMode())
					.entityName(((Attribute) attr).getEntityName())
					.build();

				if (serviceParams.get(((Attribute) attr).getName()) == null) {
					serviceParams.put(((Attribute) attr).getName(), serviceParam);
				}
			}
			else if (attr instanceof AutoAttributes) {
				String entityName = ((AutoAttributes) attr).getEntityName();
				if (entityName == null) entityName = service.getDefaultEntityName();
				log.debug("entity name: {}", entityName);

				String include = ((AutoAttributes) attr).getInclude();
				Entity entity = ofbizInstance.getAllEntities().get(entityName);
				List<Field> entityFields = OfbizUtil.getFields(entity, include);
				for (Field f : entityFields) {
					for (Exclude exclude : ((AutoAttributes) attr).getExclude()) {
						if (f.getName().equals(exclude.getFieldName())) continue;
					}

					ServiceParameter serviceParam = ServiceParameter.builder()
							.name(f.getName())
							.optional(Boolean.valueOf(((AutoAttributes) attr).getOptional()))
							.mode(((AutoAttributes) attr).getMode())
							.entityName(entityName)
							.type(FieldType.find(f).getJavaType().getName())
							.build();

					if (serviceParams.get(f.getName()) == null) {
						serviceParams.put(f.getName(), serviceParam);
					}
				}
			}
		}

		// add internal parameters
		serviceParams.put("locale", new ServiceParameter("locale", true, "java.util.Locale", "INOUT", true, null));
		serviceParams.put("login.password", new ServiceParameter("login.password", true, "String", "IN", true, null));
		serviceParams.put("login.username", new ServiceParameter("login.username", true, "String", "IN", true, null));
		serviceParams.put("timeZone", new ServiceParameter("timeZone", true, "java.util.TimeZone", "INOUT", true, null));
		serviceParams.put("userLogin", new ServiceParameter("userLogin", true, "org.apache.ofbiz.entity.GenericValue", "INOUT", true, null));

		serviceParams.put("errorMessage", new ServiceParameter("errorMessage", true, "String", "OUT", true, null));
		serviceParams.put("errorMessageList", new ServiceParameter("errorMessageList", true, "List", "OUT", true, null));
		serviceParams.put("responseMessage", new ServiceParameter("responseMessage", true, "String", "OUT", true, null));
		serviceParams.put("successMessage", new ServiceParameter("successMessage", true, "String", "OUT", true, null));
		serviceParams.put("successMessageList", new ServiceParameter("successMessageList", true, "List", "OUT", true, null));

		return serviceParams;
	}

	public static final String SERVICE_PARAMETER_MODE_IN = "IN";
	public static final String SERVICE_PARAMETER_MODE_OUT = "OUT";
	public static final String SERVICE_PARAMETER_MODE_INOUT = "INOUT";

	public Collection<ServiceParameter> getServiceInParameters(Service service) {
		Collection<ServiceParameter> allParams = getServiceParameters(service).values();
		Collection<ServiceParameter> inParams = new ArrayList<>();
		for (ServiceParameter param : allParams) {
			if (param.getMode().equals(SERVICE_PARAMETER_MODE_IN) || param.getMode().equals(SERVICE_PARAMETER_MODE_INOUT)) {
				inParams.add(param);
			}
		}
		return inParams;
	}

	public Collection<ServiceParameter> getServiceOutParameters(Service service) {
		Collection<ServiceParameter> allParams = getServiceParameters(service).values();
		Collection<ServiceParameter> outParams = new ArrayList<>();
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
