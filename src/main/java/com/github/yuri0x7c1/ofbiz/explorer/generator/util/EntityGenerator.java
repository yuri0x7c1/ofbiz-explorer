package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import lombok.Getter;
import lombok.Setter;

public abstract class EntityGenerator extends Generator {

	@Getter
	@Setter
	private String entityName;
}
