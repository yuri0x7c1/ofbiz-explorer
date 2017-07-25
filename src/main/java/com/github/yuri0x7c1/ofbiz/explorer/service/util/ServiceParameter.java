package com.github.yuri0x7c1.ofbiz.explorer.service.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ServiceParameter {
	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private boolean optional;

	@Getter
	@Setter
	private String type;

	@Getter
	@Setter
	private String mode;

	@Getter
	@Setter
	private boolean setInternally;

	@Getter
	@Setter
	private String entityName;

	/*
	@Getter
	@Setter
	private String fieldName;
	*/
}