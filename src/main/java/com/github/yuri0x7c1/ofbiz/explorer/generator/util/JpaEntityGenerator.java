package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
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
		// create entity class
		final JavaClassSource jpaEntityClass = Roaster.create(JavaClassSource.class);
		jpaEntityClass.setPackage(entity.getPackageName())
			.setName(entity.getEntityName());

		jpaEntityClass.addAnnotation(javax.persistence.Entity.class);
		/*
		jpaEntityClass.addAnnotation(Table.class)
			.setLiteralValue("name", entity.getTableName());
		*/

		Map<String, Field> fieldMap = entity.getFieldMap();

		// get primary keys
		List<String> primaryKeyNames = entity.getPrimaryKeyNames();
		if (primaryKeyNames.size() == 1) {
			Field field = fieldMap.get(primaryKeyNames.get(0));
			FieldSource<JavaClassSource> fieldSource = jpaEntityClass.addField()
				.setName(field.getName())
				.setType(field.getJavaType())
				.setPrivate();

			fieldSource.addAnnotation(Id.class);
			fieldSource.addAnnotation(Column.class);
		}
		else if (primaryKeyNames.size() > 1) {
			// composite key
		}

		// create columns
		for (Field field : entity.getField()) {
			if (!primaryKeyNames.contains(field.getName())) {
				FieldSource<JavaClassSource> fieldSource = jpaEntityClass.addField()
					.setName(field.getName())
					.setType(field.getJavaType())
					.setPrivate();


				fieldSource.addAnnotation(Column.class);
			}
		}

		return jpaEntityClass.toString();
	}


}
