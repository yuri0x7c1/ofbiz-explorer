package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.KeyMap;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Relation;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EntityGenerator {
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

	public String generate(Entity entity) throws Exception {
		log.info("Generate entity: {}", entity.getEntityName());
		// create entity class
		final JavaClassSource entityClass = Roaster.create(JavaClassSource.class);
		entityClass.setPackage(getPackageName(entity))
			.setName(entity.getEntityName());

		// add serialization stuff
		entityClass.addInterface(Serializable.class);
		entityClass.addField()
		  .setName("serialVersionUID")
		  .setType(long.class)
		  .setLiteralInitializer(String.valueOf(RandomUtils.nextLong(0, Long.MAX_VALUE-1)) + "L")
		  .setPublic()
		  .setStatic(true)
		  .setFinal(true);

		// from map method body
		StringBuilder fromValueBody = new StringBuilder();
		
		// create columns
		for (Field field : entity.getField()) {
			Class<?> fieldJavaType = field.getJavaType();
			FieldSource<JavaClassSource> fieldSource = entityClass.addField()
				.setName(field.getName())
				.setType(fieldJavaType)
				.setPrivate();

			fieldSource.addAnnotation(Getter.class);
			fieldSource.addAnnotation(Setter.class);
			
			// append param to "fromValue()" body
			fromValueBody.append(String.format("%s = %s map.get(\"%s\");",
				field.getName(),
				"(" + field.getJavaType().getName() + ")",
				field.getName()));
		}
		
		// add "fromMap()" method to nested "In" type
		MethodSource<JavaClassSource> fromValueSource = entityClass.addMethod()
			.setName("fromValue")
			.setPublic()
			.setBody(fromValueBody.toString());
		fromValueSource.addParameter("org.ofbiz.entity.GenericValue", "value");

		String destinationPath = env.getProperty("generator.destination_path");

		File src = new File(FilenameUtils.concat(destinationPath, GeneratorUtil.packageNameToPath(getPackageName(entity))), entity.getEntityName() + ".java");

		FileUtils.writeStringToFile(src,  entityClass.toString());

		return entityClass.toString();
	}


}
