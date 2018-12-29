package com.beanframework.common.data;

public class AbstractSpecification {
	protected static String convertToPattern(String value) {
		if (value.contains("%") == false && value.contains("_") == false) {
			value = "%" + value + "%";
		}
		return value;
	}
}
