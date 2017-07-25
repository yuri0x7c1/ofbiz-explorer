package com.github.yuri0x7c1.ofbiz.explorer.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Attribute;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.AutoAttributes;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceUtil {
	public static  List<ServiceParameter> getServiceParameters(Service service, OfbizInstance ofbizInstance) {
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
		return serviceParams;
	}

	public static final String SERVICE_PARAMETER_MODE_IN = "IN";
	public static final String SERVICE_PARAMETER_MODE_OUT = "OUT";
	public static final String SERVICE_PARAMETER_MODE_INOUT = "INOUT";

	public static List<ServiceParameter> getServiceInParameters(Service service, OfbizInstance ofbizInstance) {
		List<ServiceParameter> allParams = getServiceParameters(service, ofbizInstance);
		List<ServiceParameter> inParams = new ArrayList<>();
		for (ServiceParameter param : allParams) {
			if (param.getMode().equals(SERVICE_PARAMETER_MODE_IN) || param.getMode().equals(SERVICE_PARAMETER_MODE_INOUT)) {
				inParams.add(param);
			}
		}
		return inParams;
	}

	public static List<ServiceParameter> getServiceOutParameters(Service service, OfbizInstance ofbizInstance) {
		List<ServiceParameter> allParams = getServiceParameters(service, ofbizInstance);
		List<ServiceParameter> outParams = new ArrayList<>();
		for (ServiceParameter param : allParams) {
			if (param.getMode().equals(SERVICE_PARAMETER_MODE_OUT) || param.getMode().equals(SERVICE_PARAMETER_MODE_OUT)) {
				outParams.add(param);
			}
		}
		return outParams;
	}

	public static Class getParameterType(ServiceParameter parameter) {
		if ("String".equals(parameter.getType())) {
			return String.class;
		}
		else if ("Integer".equals(parameter.getType())) {
			return Integer.class;
		}
		else if ("Long".equals(parameter.getType())) {
			return Long.class;
		}
		else if ("BigDecimal".equals(parameter.getType())) {
			return BigDecimal.class;
		}
		else if ("Boolean".equals(parameter.getType())) {
			return Boolean.class;
		}
		else if ("Timestamp".equals(parameter.getType())) {
			return Date.class;
		}
		else if ("List".equals(parameter.getType())) {
			return List.class;
		}
		else if ("Map".equals(parameter.getType())) {
			return List.class;
		}
		return Object.class;
	}
}
