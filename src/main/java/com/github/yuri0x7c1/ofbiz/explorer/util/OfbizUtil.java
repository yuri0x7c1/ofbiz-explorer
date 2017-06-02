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
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance.Component;
import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance.ComponentGroup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OfbizUtil {

	public static final String ENTITYDEF_DIRECTORY_NAME = "entitydef";
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
				componentGroup.getComponents().add(component);
			}

			instance.getComponentGroups().add(componentGroup);
		}

		/*
		for (File levelOneFile: rootDirectory.listFiles()) {
			log.info("level one file {}", levelOneFile.getName());
			if (levelOneFile.isDirectory()) {
				for (File levelTwoFile : levelOneFile.listFiles()) {
					log.info("level two file {}", levelTwoFile.getName());
					if (levelTwoFile.isFile() && COMPONENT_LOAD_FILE_NAME.equals(levelTwoFile.getName())) {
						File componentDirectoryFile = levelOneFile;
						ComponentGroup componentDirectory = new ComponentGroup(componentDirectoryFile.getName());
						instance.getComponentDirectories().add(componentDirectory);

						for (File componentFile : componentDirectoryFile.listFiles()) {
							log.info("component file {}", componentFile.getName());
							if (componentFile.isDirectory() && !".settings".equals(componentFile.getName())) {
								Component component = new Component(componentFile.getName());
								componentDirectory.getComponents().add(component);

								for (File f : componentFile.listFiles()) {
									if (f.isDirectory() && ENTITYDEF_DIRECTORY_NAME.equals(f.getName())) {
										for (File entitymodelFile : f.listFiles()) {
											if (entitymodelFile.isFile() && entitymodelFile.getName().endsWith("entitymodel.xml")) {
												log.info("entitymodel file {}", entitymodelFile.getName());
												Entitymodel entitymodel = readEntitymodel(entitymodelFile);
												component.getEntities().addAll(getEntities(entitymodel));
											}
										}
									}
								}

							}
						}

					}
				}
			}
		}
		*/

		return instance;
	}
}
