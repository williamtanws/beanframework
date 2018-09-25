package com.beanframework.common.utils;

import org.apache.commons.lang3.StringUtils;

public class BooleanUtils {

	public static final boolean parseBoolean(String value){
		if(StringUtils.isEmpty(value)) {
			return false;
		}
		else if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("0") || value.equalsIgnoreCase("on")) {
			return true;
		}
		else if(value.equalsIgnoreCase("false") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("off")) {
			return false;
		}
		else {
			return false;
		}
	}
}
