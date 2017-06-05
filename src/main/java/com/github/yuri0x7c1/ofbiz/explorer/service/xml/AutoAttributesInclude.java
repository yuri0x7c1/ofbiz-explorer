package com.github.yuri0x7c1.ofbiz.explorer.service.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AutoAttributesInclude {
	PK("pk"),
	NONPK("nonpk"),
	ALL("all");

	@Getter
	private String name;

}
