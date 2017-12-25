package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.atteo.evo.inflector.English;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.KeyMap;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Relation;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EntityBaseServiceGenerator {

	public static final String PERFORM_FIND_LIST_SERVICE_NAME = "org.apache.ofbiz.common.service.PerformFindListService";

	public static final String PERFORM_FIND_ITEM_SERVICE_NAME = "org.apache.ofbiz.common.service.PerformFindItemService";

	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private Environment env;

	@Autowired
	private GeneratorHelper helper;

	private JavaClassSource createServiceClass(Entity entity) {
		JavaClassSource serviceClass = Roaster.create(JavaClassSource.class)
				.setName(entity.getEntityName() + "BaseService");

		serviceClass.addAnnotation(SuppressWarnings.class).setStringValue("unchecked");

		serviceClass.addField()
			.setName("performFindListService")
			.setType("PerformFindListService")
			.setPrivate()
			.addAnnotation(Autowired.class);

		serviceClass.addField()
			.setName("performFindItemService")
			.setType("PerformFindItemService")
			.setPrivate()
			.addAnnotation(Autowired.class);

		return serviceClass;
	}


	/**
	 * Create find method
	 * @param entity
	 * @param serviceClass
	 * @return
	 */
	private MethodSource<JavaClassSource> createFindMethod(Entity entity, JavaClassSource serviceClass) {
		MethodSource<JavaClassSource> findMethod = serviceClass.addMethod()
				.setName("find")
				.setPublic()
				.setReturnType("List<" + entity.getEntityName() + ">");

		findMethod.addParameter("Integer", "viewIndex");
		findMethod.addParameter("Integer", "viewSize");
		findMethod.addParameter("String", "orderBy");
		findMethod.addParameter("Map<String, String>", "inputFields");

		findMethod.setBody(String.format("		return %s.fromValues(\n" +
				"			performFindListService.runSync(\n" +
				"				PerformFindListService.In.builder()\n" +
				"					.entityName(%s.NAME)\n" +
				"					.viewIndex(viewIndex)\n" +
				"					.viewSize(viewSize)\n" +
				"					.inputFields(inputFields != null ? inputFields : Collections.EMPTY_MAP)\n" +
				"					.noConditionFind(MapUtils.isNotEmpty(inputFields) ? FindUtil.N : FindUtil.Y)\n" +
				"					.build()\n" +
				"			).getList()\n" +
				"		);", entity.getEntityName(), entity.getEntityName()));
		return findMethod;
	}

	/**
	 * Create count method
	 * @param entity
	 * @param serviceClass
	 * @return
	 */
	private MethodSource<JavaClassSource> createCountMethod(Entity entity, JavaClassSource serviceClass) {
		MethodSource<JavaClassSource> countMethod = serviceClass.addMethod()
				.setName("count")
				.setPublic()
				.setReturnType(Integer.class);

		countMethod.addParameter("Map<String, String>", "inputFields");

		countMethod.setBody(String.format("		return performFindListService.runSync(\n" +
				"			PerformFindListService.In.builder()\n" +
				"				.entityName(%s.NAME)\n" +
				"				.viewIndex(1)\n" +
				"				.viewSize(1)\n" +
				"				.inputFields(inputFields != null ? inputFields : Collections.EMPTY_MAP)\n" +
				"				.noConditionFind(MapUtils.isNotEmpty(inputFields) ? FindUtil.N : FindUtil.Y)\n" +
				"				.build()\n" +
				"			).getListSize();", entity.getEntityName()));
		return countMethod;
	}

	/**
	 * Create fine one method
	 * @param entity
	 * @param serviceClass
	 * @return
	 */
	private MethodSource<JavaClassSource> createFindOneMethod(Entity entity, JavaClassSource serviceClass) {
		MethodSource<JavaClassSource> findOneMethod = serviceClass.addMethod()
				.setName("findOne")
				.setPublic()
				.setReturnType(entity.getEntityName());

		StringBuilder inputFields = new StringBuilder("");

		for (String pkName : entity.getPrimaryKeyNames()) {
			findOneMethod.addParameter("String", pkName);

			inputFields.append(String.format(".addInputField(%s.Fields.%s.name(), FindUtil.OPTION_EQUALS, false, %s)\n",
					entity.getEntityName(), pkName, pkName));
		}

		findOneMethod.setBody(String.format("		return %s.fromValue(\n" +
				"			performFindItemService.runSync(\n" +
				"				PerformFindItemService.In.builder()\n" +
				"					.entityName(%s.NAME)\n" +
				"					.inputFields(\n" +
				"						new InputFieldBuilder()\n" +
				"							%s\n" +
				"							.build()\n" +
				"					).build()\n" +
				"			).getItem()\n" +
				"		);", entity.getEntityName(), entity.getEntityName(), inputFields));
		return findOneMethod;
	}

	/**
	 * Create relation methods
	 * @param entity
	 * @param serviceClass
	 */
	public void createRelationMethods(Entity entity, JavaClassSource serviceClass) {
		for (Relation relation : entity.getRelation()) {
			log.info("\tGenerate relation field for entity {}", relation.getRelEntityName());
			Entity relationEntity = ofbizInstance.getAllEntities().get(relation.getRelEntityName());
			if (relationEntity == null) {
				log.error("\tError get relation object for entity {}. Skipping relation.", relation.getRelEntityName());
			}
			else {
				String relationType = helper.getPackageName(relationEntity) + "." + relationEntity.getEntityName();
				serviceClass.addImport(relationType);

				if (Relation.TYPE_ONE.equals(relation.getType()) ||
						Relation.TYPE_ONE_NOFK.equals(relation.getType())) {

					MethodSource<JavaClassSource> getOneRelationMethod = serviceClass.addMethod()
							.setName("get" + StringUtils.capitalize(helper.getRelationFieldName(relation)))
							.setPublic()
							.setReturnType(relationType);

					String entityVariableName = StringUtils.uncapitalize(entity.getEntityName());
					getOneRelationMethod.addParameter(entity.getEntityName(), entityVariableName);

					StringBuilder inputFields = new StringBuilder("");

					for (KeyMap keyMap : relation.getKeyMap()) {
						String fieldName = keyMap.getFieldName();
						String relFieldName = keyMap.getRelFieldName() != null ? keyMap.getRelFieldName() : fieldName;
						inputFields.append(
							String.format(
								".addInputField(%s.Fields.%s.name(), FindUtil.OPTION_EQUALS, false, %s)\n",
								relationEntity.getEntityName(),
								relFieldName,
								entityVariableName + ".get" + StringUtils.capitalize(fieldName) + "()"
							)
						);

						getOneRelationMethod.setBody(String.format("		return %s.fromValue(\n" +
								"			performFindItemService.runSync(\n" +
								"				PerformFindItemService.In.builder()\n" +
								"					.entityName(%s.NAME)\n" +
								"					.inputFields(\n" +
								"						new InputFieldBuilder()\n" +
								"							%s\n" +
								"							.build()\n" +
								"					).build()\n" +
								"			).getItem()\n" +
								"		);", relationEntity.getEntityName(), relationEntity.getEntityName(), inputFields));
					}
				}
				else if(relation.TYPE_MANY.equals(relation.getType())) {
					MethodSource<JavaClassSource> getManyRelationMethod = serviceClass.addMethod()
							.setName("get" + StringUtils.capitalize(English.plural(helper.getRelationFieldName(relation))))
							.setPublic()
							.setReturnType("List<" + relationType + ">");

					String entityVariableName = StringUtils.uncapitalize(entity.getEntityName());
					getManyRelationMethod.addParameter(entity.getEntityName(), entityVariableName);
					getManyRelationMethod.addParameter("Integer", "viewIndex");
					getManyRelationMethod.addParameter("Integer", "viewSize");
					getManyRelationMethod.addParameter("String", "orderBy");

					StringBuilder inputFields = new StringBuilder("");

					for (KeyMap keyMap : relation.getKeyMap()) {
						String fieldName = keyMap.getFieldName();
						String relFieldName = keyMap.getRelFieldName() != null ? keyMap.getRelFieldName() : fieldName;
						inputFields.append(
							String.format(
								".addInputField(%s.Fields.%s.name(), FindUtil.OPTION_EQUALS, false, %s)\n",
								relationEntity.getEntityName(),
								relFieldName,
								entityVariableName + ".get" + StringUtils.capitalize(fieldName) + "()"
							)
						);

						getManyRelationMethod.setBody(String.format("		return %s.fromValues(\n" +
								"			performFindListService.runSync(\n" +
								"				PerformFindListService.In.builder()\n" +
								"				.viewIndex(viewIndex)\n" +
								"				.viewSize(viewSize)\n" +
								"					.inputFields(\n" +
								"						new InputFieldBuilder()\n" +
								"							%s\n" +
								"							.build()\n" +
								"				).build()\n" +
								"			).getList()\n" +
								"		);", relationEntity.getEntityName(), inputFields, relationEntity.getEntityName()));

						// getManyRelationMethod.setBody("return null;");
					}

				}
			}
		}
	}

	/**
	 * Generate code
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public String generate(Entity entity) throws Exception {
		log.info("Generating entity base service: {}", entity.getEntityName());

		// create service class
		final JavaClassSource serviceClass = createServiceClass(entity);

		// add some imports
		serviceClass.addImport(Collections.class);
		serviceClass.addImport(List.class);
		serviceClass.addImport(Map.class);
		serviceClass.addImport(MapUtils.class);
		serviceClass.addImport(PERFORM_FIND_LIST_SERVICE_NAME);
		serviceClass.addImport(PERFORM_FIND_ITEM_SERVICE_NAME);
		serviceClass.addImport("com.github.yuri0x7c1.uxcrm.common.find.util.FindUtil");
		serviceClass.addImport("com.github.yuri0x7c1.uxcrm.common.find.util.InputFieldBuilder");

		serviceClass.addImport(helper.getPackageName(entity) + "." + entity.getEntityName());

		// create find method
		createFindMethod(entity, serviceClass);

		// create count method
		createCountMethod(entity, serviceClass);

		// create find one method
		createFindOneMethod(entity, serviceClass);

		// create relations methods
		createRelationMethods(entity, serviceClass);

		String destinationPath = env.getProperty("generator.destination_path");

		File src = new File(FilenameUtils.concat(destinationPath, GeneratorUtil.packageNameToPath(helper.getPackageName(entity))), serviceClass.getName() + ".java");

		FileUtils.writeStringToFile(src,  serviceClass.toString());

		return serviceClass.toString();
	}
}
