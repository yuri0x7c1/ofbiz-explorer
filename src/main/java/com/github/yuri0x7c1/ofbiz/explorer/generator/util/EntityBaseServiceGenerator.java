package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EntityBaseServiceGenerator {


	@Autowired
	private OfbizInstance ofbizInstance;

	@Autowired
	private Environment env;

	@Autowired
	private GeneratorHelper helper;

	private JavaClassSource createServiceClass(Entity entity) {
		JavaClassSource serviceClass = Roaster.create(JavaClassSource.class)
				.setName(entity.getEntityName() + "BaseService");

		serviceClass.addImport(GeneratorUtil.PERFORM_FIND_LIST_SERVICE_NAME);
		serviceClass.addImport(GeneratorUtil.PERFORM_FIND_ITEM_SERVICE_NAME);

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
		serviceClass.addImport(List.class);
		serviceClass.addImport(Map.class);
		serviceClass.addImport(helper.getPackageName(entity) + "." + entity.getEntityName());
		serviceClass.addImport(MapUtils.class);

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
				"				In.builder()\n" +
				"					.entityName(%s.NAME)\n" +
				"					.viewIndex(viewIndex)\n" +
				"					.viewSize(viewSize)\n" +
				"					.inputFields(inputFields != null ? inputFields : Collections.EMPTY_MAP)\n" +
				"					.noConditionFind(MapUtils.isNotEmpty(inputFields) ? \"N\" : \"Y\")\n" +
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
		MethodSource<JavaClassSource> findMethod = serviceClass.addMethod()
				.setName("count")
				.setPublic()
				.setReturnType(Integer.class);

		findMethod.addParameter("Map<String, String>", "inputFields");

		findMethod.setBody(String.format("		return performFindListService.runSync(\n" +
				"			In.builder()\n" +
				"				.entityName(%s.NAME)\n" +
				"				.viewIndex(1)\n" +
				"				.viewSize(1)\n" +
				"				.inputFields(inputFields != null ? inputFields : Collections.EMPTY_MAP)\n" +
				"				.noConditionFind(MapUtils.isNotEmpty(inputFields) ? \"N\" : \"Y\")\n" +
				"				.build()\n" +
				"			).getListSize();", entity.getEntityName()));
		return findMethod;
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

		// create find method
		createFindMethod(entity, serviceClass);

		// create count method
		createCountMethod(entity, serviceClass);

		String destinationPath = env.getProperty("generator.destination_path");

		File src = new File(FilenameUtils.concat(destinationPath, GeneratorUtil.packageNameToPath(helper.getPackageName(entity))), entity.getEntityName() + ".java");

		FileUtils.writeStringToFile(src,  serviceClass.toString());

		return serviceClass.toString();
	}
}
