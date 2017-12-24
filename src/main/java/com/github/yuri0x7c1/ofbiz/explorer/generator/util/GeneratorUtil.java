package com.github.yuri0x7c1.ofbiz.explorer.generator.util;

import org.apache.commons.lang3.StringUtils;

public class GeneratorUtil {

	public static final String GENERIC_VALUE_CLASS_NAME = "org.apache.ofbiz.entity.GenericValue";

	public static final String PERFORM_FIND_LIST_SERVICE_NAME = "org.apache.ofbiz.common.service.PerformFindListService";

	public static final String PERFORM_FIND_ITEM_SERVICE_NAME = "org.apache.ofbiz.common.service.PerformFindItemService";

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

	/**
	 * Create phrase from camel case
	 * @param name
	 * @return
	 */
	public static String createPhraseFromCamelCase(String name) {
		String[] words = StringUtils.splitByCharacterTypeCamelCase(name);
		StringBuilder phrase = new StringBuilder();
		for (int c = 0; c < words.length; c++) {
			phrase.append(words[c].toLowerCase());
			if (c != words.length-1) {
				phrase.append(' ');
			}
		}
		return phrase.toString();
	}

	/**
	 * Create caption from camel case
	 * @param name
	 * @return
	 */
	public static String createCaptionFromCamelCase(String name) {
		String[] words = StringUtils.splitByCharacterTypeCamelCase(name);
		StringBuilder phrase = new StringBuilder();
		for (int c = 0; c < words.length; c++) {
			phrase.append(StringUtils.capitalize(words[c].toLowerCase()));
			if (c != words.length-1) {
				phrase.append(' ');
			}
		}
		return phrase.toString();
	}
}
