package com.github.yuri0x7c1.ofbiz.explorer.service.util;


import org.apache.commons.lang3.StringUtils;

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

	public String getJavaTypeName() {
		if ("String".equals(type)) {
			return "String";
		}
		else if ("Integer".equals(type)) {
			return  "Integer";
		}
		else if ("Long".equals(type)) {
			return "Long";
		}
		else if ("Double".equals(type)) {
			return "Double";
		}
		else if ("BigDecimal".equals(type)) {
			return "java.math.BigDecimal";
		}
		else if ("Boolean".equals(type)) {
			return "Boolean";
		}
		else if ("Timestamp".equals(type)) {
			return "java.sql.Timestamp";
		}
		else if ("List".equals(type)) {
			return "java.util.List";
		}
		else if ("Map".equals(type)) {
			return "java.util.Map";
		}
		else if ("Set".equals(type)) {
			return "java.util.Set";
		}
		else if ("Object".equals(type)) {
			return "Object";
		}
		else if ("Locale".equals(type)) {
			return "java.util.Locale";
		}
		else if ("GenericEntity".equals(type)) {
			return "org.apache.ofbiz.entity.GenericEntity";
		}
		else if ("GenericPK".equals(type)) {
			return "org.apache.ofbiz.entity.GenericPK";
		}
		else if ("GenericValue".equals(type)) {
			return "org.apache.ofbiz.entity.GenericValue";
		}
		else if (StringUtils.split(type, ".").length > 1) {
			return type;
		}
		return "Object";
	}

	public boolean isJavaLangType() {
		if (type.equals("String") ||
				type.equals("Integer") ||
				type.equals("Long") ||
				type.equals("Float") ||
				type.equals("Char") ||
				type.equals("Double") ||
				type.equals("Boolean")
			) {
			return true;
		}
		return false;
	}

	public boolean isText() {
		if (getJavaTypeName().equals("String")) {
			return true;
		}
		return false;
	}

	public  boolean isNumeric() {
		String javaType = getJavaTypeName();
		if (javaType.equals("Integer") || javaType.equals("Long") || javaType.equals("Double") ||
				javaType.equals("Float") || javaType.equals("java.math.BigDecimal")) {
			return true;
		}
		return false;
	}

	public boolean isBoolean() {
		if (getJavaTypeName().equals("Boolean")) {
			return true;
		}
		return false;
	}

	public boolean isTemporal() {
		if (getJavaTypeName().equals("java.sql.Timestamp")) {
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