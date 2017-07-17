package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import com.github.yuri0x7c1.ofbiz.explorer.util.OfbizInstance;

import lombok.Getter;
import lombok.Setter;

public abstract class Generator {

	@Getter
	@Setter
	private OfbizInstance ofbizInstance;

	abstract String generate();

}
