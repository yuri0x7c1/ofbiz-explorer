package com.github.yuri0x7c1.ofbiz.explorer.entity.xml;

import lombok.Getter;

public enum FieldType {
	ID_NE("id-ne"),
	ID_LONG_NE("id-long-ne"),
	ID_VLONG_NE("id-vlong-ne");

	@Getter
	private final String name;

	FieldType(String name) {
		this.name = name;
	}
}
