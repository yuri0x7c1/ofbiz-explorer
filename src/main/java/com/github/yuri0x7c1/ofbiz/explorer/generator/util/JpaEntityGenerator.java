package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomUtils;
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

		// add entity annotations
		jpaEntityClass.addAnnotation(javax.persistence.Entity.class);
		String tableName = String.format("\"%s\"", entity.getTableName() == null ? GeneratorUtil.underscoredFromCamelCaseUpper(entity.getEntityName()) : entity.getTableName());
		jpaEntityClass.addAnnotation(Table.class)
			.setLiteralValue("name", tableName);

		// add serialization stuff
		jpaEntityClass.addInterface(Serializable.class);
		jpaEntityClass.addField()
		  .setName("serialVersionUID")
		  .setType(long.class)
		  .setLiteralInitializer(String.valueOf(RandomUtils.nextLong(0, Long.MAX_VALUE-1)) + "L")
		  .setPublic()
		  .setStatic(true)
		  .setFinal(true);

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
			String columnName = String.format("\"%s\"", field.getColName() == null ? GeneratorUtil.underscoredFromCamelCaseUpper(field.getName()) : field.getColName());
			fieldSource.addAnnotation(Column.class)
				.setLiteralValue("name", columnName);
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

				String columnName = String.format("\"%s\"", field.getColName() == null ? GeneratorUtil.underscoredFromCamelCaseUpper(field.getName()) : field.getColName());
				fieldSource.addAnnotation(Column.class)
					.setLiteralValue("name", columnName);
			}
		}

		return jpaEntityClass.toString();
	}


}
