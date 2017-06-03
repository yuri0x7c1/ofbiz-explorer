package com.github.yuri0x7c1.ofbiz.explorer.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;
import com.github.yuri0x7c1.ofbiz.explorer.service.xml.Service;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * OFBiz instance representation
 * @author user
 */
public class OfbizInstance {

	private Map<String, Entity> allEntities = new TreeMap<>();

	private Map<String, Service> allServices = new TreeMap<>();

	/**
	 * Component group representation
	 * @author user
	 */
	@RequiredArgsConstructor
	public static class ComponentGroup {
		@Getter
		@Setter
		@NonNull
		private String name;

		@Getter
		@Setter
		private Map<String, Component> components = new TreeMap<>();
	}

	/**
	 * Component representation
	 * @author user
	 */
	@RequiredArgsConstructor
	public static class Component {
		@Getter
		@Setter
		@NonNull
		private String name;

		@Getter
		@Setter
		private Map<String, Entity> entities = new TreeMap<>();

		@Getter
		@Setter
		private Map<String, Service> services = new TreeMap<>();
	}

	@Getter
	@Setter
	private Map<String, ComponentGroup> componentGroups = new TreeMap<>();

	/**
	 * Return all OFBiz entities
	 * @return
	 */
	public Map<String, Entity> getAllEntities() {
		if (allEntities.isEmpty()) {
			componentGroups.forEach((componentGroupName, componentGroup) -> {
				componentGroup.components.forEach((componentName, component) -> {
					allEntities.putAll(component.getEntities());
				});
			});
		}

		return allEntities;
	}

	/**
	 * Return all OFBiz services
	 * @return
	 */
	public Map<String, Service> getAllServices() {
		if (allEntities.isEmpty()) {
			componentGroups.forEach((componentGroupName, componentGroup) -> {
				componentGroup.components.forEach((componentName, component) -> {
					allServices.putAll(component.getServices());
				});
			});
		}

		return allServices;
	}

}
