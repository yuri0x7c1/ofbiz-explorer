package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.mapping.Map;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import antlr.collections.List;
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
		return serviceClass;
	}


	private MethodSource<JavaClassSource> createFindMethod(Entity entity, JavaClassSource serviceClass) {
		serviceClass.addImport(List.class);
		serviceClass.addImport(Map.class);
		serviceClass.addImport(helper.getPackageName(entity) + "." + entity.getEntityName());

		MethodSource<JavaClassSource> findMethod = serviceClass.addMethod()
				.setName("find")
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
				"					.noConditionFind(MapUtils.isNotEmpty(inputFields) ? FindUtil.N : FindUtil.Y)\n" +
				"					.build()\n" +
				"			).getList()\n" +
				"		);", entity.getEntityName(), entity.getEntityName()));
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

		String destinationPath = env.getProperty("generator.destination_path");

		File src = new File(FilenameUtils.concat(destinationPath, GeneratorUtil.packageNameToPath(helper.getPackageName(entity))), entity.getEntityName() + ".java");

		FileUtils.writeStringToFile(src,  serviceClass.toString());

		return serviceClass.toString();
	}
}
