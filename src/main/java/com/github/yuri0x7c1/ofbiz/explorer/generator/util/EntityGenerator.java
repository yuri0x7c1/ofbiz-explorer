package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.FieldType;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Relation;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EntityGenerator {

	public static final String GENERIC_VALUE_CLASS_NAME = "org.apache.ofbiz.entity.GenericValue";

	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private Environment env;

	public String getRelationFieldName(Relation relation) {
		StringBuilder name = new StringBuilder();
		if (!StringUtils.isBlank(relation.getTitle())) {
			name.append(relation.getTitle())
				.append(relation.getRelEntityName());
		}
		else {
			name.append(relation.getRelEntityName());
		}

		name.setCharAt(0, Character.toLowerCase(name.charAt(0)));
		if (relation.getKeyMap().size() == 1) {
			if (name.toString().equals(relation.getKeyMap().get(0).getFieldName())) {
				name.append("Relation");
			}
		}

		return StringUtils.uncapitalize(name.toString());
	}

	public String getPackageName(Entity entity) {
		String packageName = entity.getPackageName().replace("return", "_return").replace("enum", "_enum");

		String basePackage = env.getProperty("generator.base_package");
		if (basePackage != null && !basePackage.equals("org.apache.ofbiz")) {
			packageName = packageName.replace("org.apache.ofbiz", basePackage);
		}

		String entityPackage = env.getProperty("generator.entity.package");
		if (entityPackage != null) {
			packageName += "." + entityPackage;
		}
		return  packageName;
	}

	/**
	 * Create entity class
	 * @param entity
	 * @return
	 */
	private JavaClassSource createEntityClass(Entity entity) {
		// create entity class
		final JavaClassSource entityClass = Roaster.create(JavaClassSource.class);
		entityClass.setPackage(getPackageName(entity))
			.setName(entity.getEntityName());

		// comment
		entityClass.getJavaDoc().setFullText(GeneratorUtil.createCaptionFromCamelCase(entity.getEntityName()));

		// add serialization stuff
		entityClass.addInterface(Serializable.class);
		entityClass.addField()
		  .setName("serialVersionUID")
		  .setType(long.class)
		  .setLiteralInitializer(String.valueOf(RandomUtils.nextLong(0, Long.MAX_VALUE-1)) + "L")
		  .setPublic()
		  .setStatic(true)
		  .setFinal(true);

		// create columns
		for (Field field : entity.getField()) {
			Class<?> fieldJavaType = FieldType.find(field).getJavaType();
			FieldSource<JavaClassSource> entityField = entityClass.addField()
				.setName(field.getName())
				.setType(fieldJavaType)
				.setPrivate();

			// add comment
			entityField.getJavaDoc().setFullText(GeneratorUtil.createCaptionFromCamelCase(field.getName()));

			// add lombok getters an setters
			entityField.addAnnotation(Getter.class);
			entityField.addAnnotation(Setter.class);
		}

		return entityClass;
	}

	/**
	 * Create constructor with GenericValue entity parameter
	 * @param entity
	 * @param entityClass
	 */
	private void createConstructorWithGenericValueParameter(Entity entity, JavaClassSource entityClass) {
		// create columns
		StringBuilder constructorBody = new StringBuilder();

		// constructor body
		for (Field field : entity.getField()) {
			// append param to "fromValue()" body
			constructorBody.append(String.format("%s = %s value.get(\"%s\");",
				field.getName(),
				"(" + FieldType.find(field).getJavaType().getSimpleName() + ")",
				field.getName()));
		}

		MethodSource<JavaClassSource> constructor = entityClass.addMethod()
			.setConstructor(true)
			.setPublic()
			.setBody(constructorBody.toString());

		constructor.addParameter(GENERIC_VALUE_CLASS_NAME, "value");
	}

	/**
	 * Create fromValue static method
	 * @param entity
	 * @param entityClass
	 */
	private void createFromValueStaticMethod(Entity entity, JavaClassSource entityClass) {
		String fromValueMethodBody = "return new " + entityClass.getName() + "(value);";

		MethodSource<JavaClassSource> fromValueMethod = entityClass.addMethod()
			.setName("fromValue")
			.setPublic()
			.setStatic(true)
			.setReturnType(entityClass)
			.setBody(fromValueMethodBody);

		fromValueMethod.addParameter(GENERIC_VALUE_CLASS_NAME, "value");
	}

	/**
	 * Create fromValues static method
	 * @param entity
	 * @param entityClass
	 */
	private void createFromValuesStaticMethod(Entity entity, JavaClassSource entityClass) {
		entityClass.addImport(List.class);
		entityClass.addImport(ArrayList.class);

		String fromValuesMethodBody = String.format("List<%s> entities = new ArrayList<>();"
				+ "for (GenericValue value : values) {"
				+ "		entities.add(new %s(value));"
				+ "}"
				+ "return entities;", entityClass.getName(), entityClass.getName());

		MethodSource<JavaClassSource> fromValuesMethod = entityClass.addMethod()
			.setName("fromValues")
			.setPublic()
			.setStatic(true)
			.setReturnType("List<" + entityClass.getName() + ">")
			.setBody(fromValuesMethodBody);

			fromValuesMethod.addParameter("List<GenericValue>", "values");
	}

	/**
	 * Generate code
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public String generate(Entity entity) throws Exception {
		log.info("Generate entity: {}", entity.getEntityName());

		// create entity class
		final JavaClassSource entityClass = createEntityClass(entity);

		// create constructor
		createConstructorWithGenericValueParameter(entity, entityClass);

		// create fromValue static method
		createFromValueStaticMethod(entity, entityClass);

		// create fromValues static method
		createFromValuesStaticMethod(entity, entityClass);

		String destinationPath = env.getProperty("generator.destination_path");

		File src = new File(FilenameUtils.concat(destinationPath, GeneratorUtil.packageNameToPath(getPackageName(entity))), entity.getEntityName() + ".java");

		FileUtils.writeStringToFile(src,  entityClass.toString());

		return entityClass.toString();
	}
}
