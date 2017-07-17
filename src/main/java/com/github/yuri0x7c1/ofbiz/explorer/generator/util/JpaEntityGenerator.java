package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.Getter;

public class JpaEntityGenerator {
	@Getter
	private OfbizInstance ofbizInstance;

	@Getter
	private String entityName;

	@Getter
	private Entity entity;

	public JpaEntityGenerator(OfbizInstance ofbizInstance, String entityName) {
		super();
		this.ofbizInstance = ofbizInstance;
		this.entityName = entityName;

		this.entity = ofbizInstance.getAllEntities().get(entityName);
	}

	public String generate() {
		final JavaClassSource jpaEntityClass = Roaster.create(JavaClassSource.class);
		jpaEntityClass.setPackage(entity.getPackageName())
			.setName(entity.getEntityName());

		for (Field field : entity.getField()) {
			jpaEntityClass.addField()
				.setName(field.getName())
				.setType(getJavaType(field.getType()))
				.setPrivate();
		}

		return jpaEntityClass.toString();
	}

	private String getJavaType(String type) {
		if ("blob".equals(type)) {
			return "java.sql.Blob";
		} else if ("object".equals(type)) {
			return "Object";
		} else if ("byte-array".equals(type)) {
			return "byte[]";

		} else if ("currency-precise".equals(type)) {
			return "BigDecimal";
		} else if ("currency-amount".equals(type)) {
			return "BigDecimal";
		} else if ("fixed-point".equals(type)) {
			return "BigDecimal";

		} else if ("date".equals(type)) {
			return "Date";
		} else if ("time".equals(type)) {
			return "Time";
		} else if ("date-time".equals(type)) {
			return "Date";

		} else if ("numeric".equals(type)) {
			return "Long";
		} else if ("floating-point".equals(type)) {
			return "Double";
		}

		return "String";
}
}
