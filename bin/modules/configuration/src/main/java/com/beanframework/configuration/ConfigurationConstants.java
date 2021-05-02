package com.beanframework.configuration;

public interface ConfigurationConstants {

	public interface Table {
		public static final String CONFIGURATION = "configuration";
	}

	public static interface Locale {
		public static final String ID_REQUIRED = "id.required";
		public static final String ID_EXISTS = "id.exists";
		public static final String UUID_NOT_EXISTS = "uuid.notexists";;
	}

}
