package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import org.apache.commons.lang3.StringUtils;

public class GeneratorUtil {
	public static String underscoredFromCamelCaseLower(String camelCaseString) {
		String[] words = StringUtils.splitByCharacterTypeCamelCase(camelCaseString);
		return StringUtils.join(words, "_").toLowerCase();
	}

	public static String underscoredFromCamelCaseUpper(String camelCaseString) {
		String[] words = StringUtils.splitByCharacterTypeCamelCase(camelCaseString);
		return StringUtils.join(words, "_").toUpperCase();
	}

	/**
	 * Converts package name to file system path
	 * @param packageName
	 * @return
	 */
	public static String packageNameToPath(String packageName) {
		return packageName.replaceAll("\\.", "/");
	}
}
