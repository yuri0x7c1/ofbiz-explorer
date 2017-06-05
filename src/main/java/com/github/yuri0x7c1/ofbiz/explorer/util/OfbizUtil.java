package com.github.yuri0x7c1.ofbiz.explorer.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.boot.ApplicationHome;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entitymodel;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Field;
import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.FieldType;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Attribute;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.AutoAttributesInclude;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Services;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance.Component;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance.ComponentGroup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OfbizUtil {

	public static final String ENTITYDEF_DIRECTORY_NAME = "entitydef";
	public static final String SERVICEDEF_DIRECTORY_NAME = "servicedef";
	public static final String COMPONENT_LOAD_FILE_NAME = "component-load.xml";

	/**
	 * Read entity model file
	 * @param entitymodelFile
	 * @return
	 */
	public static Entitymodel readEntitymodel(File entitymodelFile) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("com.github.yuri0x7c1.ofbiz.explorer.entity.xml");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			Entitymodel entitymodel  = (Entitymodel) unmarshaller.unmarshal(
					new FileInputStream(entitymodelFile));

			return entitymodel;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Read services file
	 * @param servicesFile
	 * @return
	 */
	public static Services readServices(File servicesFile) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("com.github.yuri0x7c1.ofbiz.explorer.service.xml");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			Services services  = (Services) unmarshaller.unmarshal(
					new FileInputStream(servicesFile));

			return services;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Get entity list from entitymodel
	 * @param entitymodel
	 * @return
	 */
	public static List<Entity> getEntities(Entitymodel entitymodel) {
		List<Entity> entities = new ArrayList<>();
		for (Object o : entitymodel.getEntityOrViewEntityOrExtendEntity()) {
			if (o instanceof Entity) {
				entities.add((Entity) o);
			}
		}
		return entities;
	}

	/**
	 * Get OFBiz root path
	 * @return
	 */
	public static String getOfbizRootPath() {
		ApplicationHome home = new ApplicationHome();
		log.info("application home dir {}", home.getDir());
		return home.getDir() + "/src/test/resources/apache-ofbiz-16.11.02";
	}

	/**
	 * Read OFBiz component group directories
	 * @return
	 */
	public static List<File> readOfbizComponentGroupDirectories() {
		File rootDirectory = new File(getOfbizRootPath());
		List<File> componentGroupDirs = new ArrayList<>();

		for (File levelOneFile: rootDirectory.listFiles()) {
			log.info("level one file {}", levelOneFile.getName());
			if (levelOneFile.isDirectory()) {
				for (File levelTwoFile : levelOneFile.listFiles()) {
					log.info("level two file {}", levelTwoFile.getName());
					if (levelTwoFile.isFile() && COMPONENT_LOAD_FILE_NAME.equals(levelTwoFile.getName())) {
						componentGroupDirs.add(levelOneFile);
					}
				}
			}
		}

		return componentGroupDirs;
	}

	/**
	 * Read OFBiz component directories
	 * @param componentGroupDir
	 * @return
	 */
	public static List<File> readComponentDirectories(File componentGroupDir) {
		List<File> componentDirs = new ArrayList<>();

		for (File componentDir : componentGroupDir.listFiles()) {
			if (componentDir.isDirectory()) { // TODO: check if directory contains ofbiz-component.xml file
				log.info("component dir name {}", componentDir.getName());
				componentDirs.add(componentDir);
			}
		}

		return componentDirs;
	}

	/**
	 * Read OFBiz instance structure
	 * @return
	 */
	public static OfbizInstance readInstance() {
		OfbizInstance instance = new OfbizInstance();
		File rootDirectory = new File("/home/user/Programs/apache-ofbiz-16.11.01");

		List<File> componentGroupDirs = readOfbizComponentGroupDirectories();
		for (File componentGroupDir : componentGroupDirs) {
			ComponentGroup componentGroup = new ComponentGroup(componentGroupDir.getName());

			List<File> componentDirs = readComponentDirectories(componentGroupDir);
			for (File componentDir : componentDirs) {
				Component component = new Component(componentDir.getName()); // TODO: read component name from ofbiz-component.xml
				componentGroup.getComponents().put(component.getName(), component);

				for (File f : componentDir.listFiles()) {
					if (f.isDirectory()) {
						// TODO: move this block to method returning map of entities
						if (ENTITYDEF_DIRECTORY_NAME.equals(f.getName())) {
							for (File entitymodelFile : f.listFiles()) {
								if (entitymodelFile.isFile() && entitymodelFile.getName().endsWith("entitymodel.xml")) {
									log.info("entitymodel file {}", entitymodelFile.getName());
									Entitymodel entitymodel = readEntitymodel(entitymodelFile);
									getEntities(entitymodel).forEach(entity -> {
										component.getEntities().put(entity.getEntityName(), entity);
									});

								}
							}
						} // TODO: move this block to method returning map of services
						else if (SERVICEDEF_DIRECTORY_NAME.equals(f.getName())) {
							for (File servicesFile : f.listFiles()) {
								if (servicesFile.isFile() && servicesFile.getName().startsWith("services")) {
									log.info("servicedef file {}", servicesFile.getName());
									Services services = readServices(servicesFile);
									services.getService().forEach(service -> {
										component.getServices().put(service.getName(), service);
									});

								}
							}
						}
					}
				}
			}

			instance.getComponentGroups().put(componentGroup.getName(), componentGroup);
		}

		return instance;
	}

	public static List<Attribute> filterServiceAttributes(List<Object> autoAttributesOrAttribute) {
		List<Attribute> attributes = new ArrayList<>();
		for (Object attr : autoAttributesOrAttribute) {
			if (attr instanceof Attribute) {
				attributes.add((Attribute) attr);
			}
		}
		return attributes;
	}

	/**
	 * Get pk fields from entity field list
	 * @param entity
	 * @return
	 */
	public static List<Field> getPkFields(Entity entity) {
		List<Field> pkFields = new ArrayList<>();
		for (Field field : entity.getField()) {
			if (FieldType.ID_NE.getName().equals(field.getType()) ||
					FieldType.ID_LONG_NE.getName().equals(field.getType()) ||
					FieldType.ID_LONG_NE.getName().equals(field.getType())) {
				pkFields.add(field);
			}
		}
		return pkFields;
	}

	/**
	 * Get non pk fields from entity field list
	 * @param entity
	 * @return
	 */
	public static List<Field> getNonpkFields(Entity entity) {
		List<Field> nonpkFields = new ArrayList<>();
		for (Field field : entity.getField()) {
			if (!FieldType.ID_NE.getName().equals(field.getType()) &&
					!FieldType.ID_LONG_NE.getName().equals(field.getType()) &&
					!FieldType.ID_LONG_NE.getName().equals(field.getType())) {
				nonpkFields.add(field);
			}
		}
		return nonpkFields;
	}

	/**
	 * Get fields from entity qualified by auto-attributes include parameter
	 * @param entity
	 * @param include
	 * @return list of fields or empty list
	 */
	public static List<Field> getFields(Entity entity, String include) {
		if (AutoAttributesInclude.PK.getName().equals(include)) {
			return getPkFields(entity);
		}
		else if (AutoAttributesInclude.NONPK.getName().equals(include)) {
			return getNonpkFields(entity);
		}
		else if (AutoAttributesInclude.ALL.getName().equals(include)) {
			return entity.getField();
		}
		return new ArrayList<>();
	}
}
