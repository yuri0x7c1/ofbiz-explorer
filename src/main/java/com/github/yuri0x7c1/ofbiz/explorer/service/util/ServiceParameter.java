package com.github.yuri0x7c1.ofbiz.explorer.service.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ServiceParameter {
	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private boolean optional;

	@Getter
	@Setter
	private String type;

	@Getter
	@Setter
	private String mode;

	@Getter
	@Setter
	private boolean setInternally;

	@Getter
	@Setter
	private String entityName;

	public ServiceParameter(String name, boolean optional, String type, String mode, boolean setInternally,
			String entityName) {
		super();
		this.name = name;
		this.optional = optional;
		this.type = type;
		this.mode = mode;
		this.setInternally = setInternally;
		this.entityName = entityName;
	}

	/*
	@Getter
	@Setter
	private String fieldName;
	*/

	public Class<?> getJavaType() {
		if ("String".equals(type)) {
			return String.class;
		}
		else if ("Integer".equals(type)) {
			return Integer.class;
		}
		else if ("Long".equals(type)) {
			return Long.class;
		}
		else if ("BigDecimal".equals(type)) {
			return BigDecimal.class;
		}
		else if ("Boolean".equals(type)) {
			return Boolean.class;
		}
		else if ("Timestamp".equals(type)) {
			return Date.class;
		}
		else if ("List".equals(type)) {
			return List.class;
		}
		else if ("Map".equals(type)) {
			return List.class;
		}
		return Object.class;
	}

	public boolean isText() {
		if (getJavaType().equals(String.class)) {
			return true;
		}
		return false;
	}

	public  boolean isNumeric() {
		Class<?> javaType = getJavaType();
		if (javaType.equals(Integer.class) || javaType.equals(Long.class) || javaType.equals(Double.class) ||
				javaType.equals(Float.class) || javaType.equals(BigDecimal.class)) {
			return true;
		}
		return false;
	}

	public boolean isBoolean() {
		if (getJavaType().equals(Boolean.class)) {
			return true;
		}
		return false;
	}

	public boolean isTemporal() {
		if (getJavaType().equals(Date.class)) {
			return true;
		}
		return false;
	}

	public String getterName() {
		return "get" + StringUtils.capitalize(name);
	}

	public String setterName() {
		return "set" + StringUtils.capitalize(name);
	}
}