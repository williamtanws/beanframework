package com.beanframework.common.utils;

import java.util.UUID;

public class ParamUtils {

	public static String parseString(Object object) {

		if (object == null) {
			return null;
		} else {
			try {
				return String.valueOf(object);
			} catch (Exception e) {
				return null;
			}

		}
	}

	public static int parseInt(Object object) {

		if (object == null) {
			return 0;
		} else {
			try {
				return Integer.valueOf(String.valueOf(object));
			} catch (Exception e) {
				return 0;
			}
		}
	}

	public static UUID parseUUID(Object object) {

		if (object == null) {
			return null;
		} else {
			try {
				return UUID.fromString(String.valueOf(object));
			} catch (Exception e) {
				return null;
			}

		}
	}

	public static boolean parseBoolean(Object object) {

		if (object == null) {
			return false;
		} else {
			String result;
			try {
				result = String.valueOf(object);
			} catch (Exception e) {
				return false;
			}

			if (result.equalsIgnoreCase("true") || result.equals("0")) {
				return true;
			} else {
				return false;
			}
		}
	}

}
