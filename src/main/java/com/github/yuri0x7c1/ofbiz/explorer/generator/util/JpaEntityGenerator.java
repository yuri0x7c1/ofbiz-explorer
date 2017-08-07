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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.KeyMap;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Relation;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JpaEntityGenerator {
	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private Environment env;

	private boolean isJoinColumnModifiable(Entity entity, List<KeyMap> relKeyMaps) {
		return false;
	}

	private AnnotationSource<JavaClassSource>  setJoinColumnValue(AnnotationSource<JavaClassSource> annotationSource, KeyMap relKeyMap, boolean modifiable) {
		String joinColumnName = GeneratorUtil.underscoredFromCamelCaseUpper(relKeyMap.getFieldName());
		String joinColumnReferencedName = joinColumnName;
		if (relKeyMap.getRelFieldName() != null) joinColumnReferencedName = GeneratorUtil.underscoredFromCamelCaseUpper(relKeyMap.getRelFieldName());
		annotationSource.setLiteralValue("name", "\"" + joinColumnName + "\"");
		annotationSource.setLiteralValue("referencedColumnName", "\"" + joinColumnReferencedName + "\"");

		if (!modifiable) {
			// annotationSource.setLiteralValue("nullable", Boolean.FALSE.toString());
			annotationSource.setLiteralValue("insertable", Boolean.FALSE.toString());
			annotationSource.setLiteralValue("updatable", Boolean.FALSE.toString());
		}

		return annotationSource;
	}

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
		return entity.getPackageName().replace("return", "_return").replace("enum", "_enum");
	}

	public String generate(Entity entity) throws Exception {
		log.info("Generate entity: {}", entity.getEntityName());
		// create entity class
		final JavaClassSource jpaEntityClass = Roaster.create(JavaClassSource.class);
		jpaEntityClass.setPackage(getPackageName(entity))
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
		if (primaryKeyNames.size() == 1) { // single column id
			Field field = fieldMap.get(primaryKeyNames.get(0));
			FieldSource<JavaClassSource> fieldSource = jpaEntityClass.addField()
				.setName(field.getName())
				.setType(field.getJavaType())
				.setPrivate();

			fieldSource.addAnnotation(Getter.class);
			fieldSource.addAnnotation(Setter.class);
			fieldSource.addAnnotation(Id.class);
			String columnName = String.format("\"%s\"", field.getColName() == null ? GeneratorUtil.underscoredFromCamelCaseUpper(field.getName()) : field.getColName());
			fieldSource.addAnnotation(Column.class)
				.setLiteralValue("name", columnName);
		}
		else if (primaryKeyNames.size() > 1) { // composite id
			// create id class
			JavaClassSource jpaEntityIdClass = Roaster.create(JavaClassSource.class);
			jpaEntityIdClass.setName(entity.getEntityName() + "Id");

			// add annotation
			jpaEntityIdClass.addAnnotation(Embeddable.class);
			jpaEntityClass.addImport(Embeddable.class);

			// add fields
			for (String primaryKeyName : primaryKeyNames) {
				Field field = fieldMap.get(primaryKeyName);
				Class<?> fieldJavaType = field.getJavaType();
				FieldSource<JavaClassSource> fieldSource = jpaEntityIdClass.addField()
					.setName(field.getName())
					.setType(fieldJavaType)
					.setPrivate();

				// date import hack
				if (fieldJavaType.equals(java.util.Date.class)) {
					jpaEntityClass.addImport(java.util.Date.class);
				}

				fieldSource.addAnnotation(Getter.class);
				fieldSource.addAnnotation(Setter.class);

				String columnName = String.format("\"%s\"", field.getColName() == null ? GeneratorUtil.underscoredFromCamelCaseUpper(field.getName()) : field.getColName());
				fieldSource.addAnnotation(Column.class).setLiteralValue("name", columnName);

			}
			// import column annotation
			jpaEntityClass.addImport(Column.class);

			// add id class
			jpaEntityClass.addNestedType(jpaEntityIdClass).setPublic().setStatic(true);

			// add id field
			FieldSource<JavaClassSource> fieldSource = jpaEntityClass.addField().setName("id").setType(jpaEntityIdClass).setPrivate();
			fieldSource.addAnnotation(Getter.class);
			fieldSource.addAnnotation(Setter.class);
			fieldSource.addAnnotation(EmbeddedId.class);
		}

		// create columns
		for (Field field : entity.getField()) {
			if (!primaryKeyNames.contains(field.getName())) {
				FieldSource<JavaClassSource> fieldSource = jpaEntityClass.addField()
					.setName(field.getName())
					.setType(field.getJavaType())
					.setPrivate();

				String columnName = String.format("\"%s\"", field.getColName() == null ? GeneratorUtil.underscoredFromCamelCaseUpper(field.getName()) : field.getColName());
				fieldSource.addAnnotation(Getter.class);
				fieldSource.addAnnotation(Setter.class);
				fieldSource.addAnnotation(Column.class)
					.setLiteralValue("name", columnName);
			}
		}

		// create relations
		for (Relation relation : entity.getRelation()) {
			log.info("\tGenerate relation field for entity {}", relation.getRelEntityName());
			Entity relationEntity = ofbizInstance.getAllEntities().get(relation.getRelEntityName());
			if (relationEntity == null) {
				log.error("\tError get relation object for entity {}. Skipping relation.", relation.getRelEntityName());
			}
			else {
				String relationType = getPackageName(relationEntity) + "." + relationEntity.getEntityName();
				FieldSource<JavaClassSource> relationFieldSource = jpaEntityClass.addField()
					.setName(getRelationFieldName(relation))
					.setType(relationType)
					.setPrivate();

				relationFieldSource.addAnnotation(Getter.class);
				// relationFieldSource.addAnnotation(Setter.class);

				if (Relation.TYPE_ONE.equals(relation.getType())) {
					relationFieldSource.addAnnotation(OneToOne.class);
				}
				else if (Relation.TYPE_MANY.equals(relation.getType())) {
					relationFieldSource.addAnnotation(OneToMany.class);
				}

				boolean relModifiable = isJoinColumnModifiable(entity, relation.getKeyMap());
				if (relation.getKeyMap().size() == 1) {
					setJoinColumnValue(relationFieldSource.addAnnotation(JoinColumn.class), relation.getKeyMap().get(0), relModifiable);
				}
				else if (relation.getKeyMap().size() > 1) {
					AnnotationSource<JavaClassSource> joinColumnsSource = relationFieldSource.addAnnotation(JoinColumns.class);
					for (KeyMap relKeyMap : relation.getKeyMap()) {
						setJoinColumnValue(joinColumnsSource.addAnnotationValue(JoinColumn.class), relKeyMap, relModifiable);
					}

				}
			}
		}

		String destinationPath = env.getProperty("generator.destination_path");

		File src = new File(FilenameUtils.concat(destinationPath, GeneratorUtil.packageNameToPath(getPackageName(entity))), entity.getEntityName() + ".java");

		FileUtils.writeStringToFile(src,  jpaEntityClass.toString());

		return jpaEntityClass.toString();
	}


}
