package com.github.yuri0x7c1.ofbiz.explorer.util;

import java.util.ArrayList;
import java.util.List;

import com.github.yuri0x7c1.ofbiz.explorer.entity.xml.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * OFBiz instance representation
 * @author user
 */
public class OfbizInstance {

	/**
	 * Component directory representation
	 * @author user
	 */
	@RequiredArgsConstructor
	public static class ComponentDirectory {
		@Getter
		@Setter
		@NonNull
		private String name;

		@Getter
		@Setter
		private List<Component> components = new ArrayList<>();
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
		private List<Entity> entities = new ArrayList<>();
	}

	@Getter
	@Setter
	private List<ComponentDirectory> componentDirectories = new ArrayList<>();

}
