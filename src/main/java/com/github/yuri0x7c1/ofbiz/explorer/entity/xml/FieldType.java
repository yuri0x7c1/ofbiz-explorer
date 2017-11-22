package com.github.yuri0x7c1.ofbiz.explorer.entity.xml;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import lombok.Getter;


public enum FieldType {
	/*
	    <!-- General Types -->
	    <field-type-def type="blob" sql-type="BYTEA" java-type="java.sql.Blob"/>
	    <field-type-def type="byte-array" sql-type="BYTEA" java-type="byte[]"/>
	    <field-type-def type="object" sql-type="BYTEA" java-type="Object"/>
	*/
	BLOB("blob", java.sql.Blob.class),
	BYTE_ARRAY("byte-array", byte[].class),
	OBJECT("object", Object.class),

	/*
	    <field-type-def type="date-time" sql-type="TIMESTAMPTZ" java-type="java.sql.Timestamp"/>
	    <field-type-def type="date" sql-type="DATE" java-type="java.sql.Date"/>
	    <field-type-def type="time" sql-type="TIME" java-type="java.sql.Time"/>
	*/
	DATE_TIME("date-time", java.sql.Timestamp.class),
	DATE("date", java.sql.Date.class),
	TIME("time", java.sql.Time.class),

	/*
	    <field-type-def type="currency-amount" sql-type="NUMERIC(18,2)" java-type="java.math.BigDecimal"/>
	    <field-type-def type="currency-precise" sql-type="NUMERIC(18,3)" java-type="java.math.BigDecimal"/>
	    <field-type-def type="fixed-point" sql-type="NUMERIC(18,6)" java-type="java.math.BigDecimal"/>
	    <field-type-def type="floating-point" sql-type="FLOAT8" java-type="Double"/>
	    <field-type-def type="numeric" sql-type="NUMERIC(20,0)" java-type="Long"/>
	*/
	CURRENCY_AMOUNT("currency-amount", java.math.BigDecimal.class),
	CURRENCY_PRECISE("currency-precise", java.math.BigDecimal.class),
	FIXED_POINT("fixed-point", java.math.BigDecimal.class),
	FLOATING_POINT("floating-point", Double.class),
	NUMERIC("numeric", Long.class),

	/*
	    <field-type-def type="id" sql-type="VARCHAR(20)" java-type="String"/>
	    <field-type-def type="id-long" sql-type="VARCHAR(60)" java-type="String"/>
	    <field-type-def type="id-vlong" sql-type="VARCHAR(255)" java-type="String"/>
	*/
	ID("id", String.class),
	ID_LONG("id-long", String.class),
	ID_VLONG("id-vlong", String.class),

	/*
	    <field-type-def type="indicator" sql-type="CHAR(1)" sql-type-alias="BPCHAR" java-type="String"/>
	    <field-type-def type="very-short" sql-type="VARCHAR(10)" java-type="String"/>
	    <field-type-def type="short-varchar" sql-type="VARCHAR(60)" java-type="String"/>
	    <field-type-def type="long-varchar" sql-type="VARCHAR(255)" java-type="String"/>
	    <field-type-def type="very-long" sql-type="TEXT" java-type="String"/>
	*/
	INDICATOR("indicator", String.class),
	VERY_SHORT("very-short", String.class),
	SHORT_VARCHAR("short-varchar", String.class),
	LONG_VARCHAR("long-varchar", String.class),
	VERY_LONG("very-long", String.class),

	/*
	    <field-type-def type="comment" sql-type="VARCHAR(255)" java-type="String"/>
	    <field-type-def type="description" sql-type="VARCHAR(255)" java-type="String"/>
	    <field-type-def type="name" sql-type="VARCHAR(100)" java-type="String"/>
	    <field-type-def type="value" sql-type="VARCHAR(255)" java-type="String"/>
	*/
	COMMENT("comment", String.class),
	DESCRIPTION("description", String.class),
	NAME("name", String.class),
	VALUE("value", String.class),

	/*
	    <!-- Specialized Types -->
	    <field-type-def type="credit-card-number" sql-type="VARCHAR(255)" java-type="String"/>
	    <field-type-def type="credit-card-date" sql-type="VARCHAR(7)" java-type="String"/>
	    <field-type-def type="email" sql-type="VARCHAR(320)" java-type="String"/>
	    <field-type-def type="url" sql-type="VARCHAR(2000)" java-type="String"/>
	    <field-type-def type="id-ne" sql-type="VARCHAR(20)" java-type="String"/>
	    <field-type-def type="id-long-ne" sql-type="VARCHAR(60)" java-type="String"/>
	    <field-type-def type="id-vlong-ne" sql-type="VARCHAR(255)" java-type="String"/>
	    <field-type-def type="tel-number" sql-type="VARCHAR(60)" java-type="String"/>
	 */
	CREDIT_CARD_NUMBER("credit-card-number", String.class),
	CREDIT_CARD_DATE("credit-card-date", String.class),
	EMAIL("email", String.class),
	URL("url", String.class),
	ID_NE("id-ne", String.class),
	ID_LONG_NE("id-long-ne", String.class),
	ID_VLONG_NE("id-vlong-ne", String.class),
	TEL_NUMBER("tel-number", String.class),

	UNKNOWN("unknown", String.class);

	@Getter
	private final String name;

	private final Class<?> javaType;

	private final List<Annotation> annotations = new ArrayList<>();

	FieldType(String name, Class<?> javaType) {
		this.name = name;
		this.javaType = javaType;
	}

	FieldType(String name, Class<?> javaType, Annotation... annotations) {
		this.name = name;
		this.javaType = javaType;
		this.annotations.addAll(Arrays.asList(annotations));
	}

	FieldType findByName(String name) {
		for (FieldType fieldType : values()) {
			if (fieldType.name.equals(name)) {
				return fieldType;
			}
		}
		return UNKNOWN;
	}
}
