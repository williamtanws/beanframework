package com.beanframework.dynamicfield;

public class DynamicFieldConstants {

	public static interface Table {
		public static final String DYNAMIC_FIELD = "dynamicfield";
		public static final String DYNAMIC_FIELD_ATTRIBUTE = "dynamicfieldattr";
	}
	
	public static interface Locale {
		public static final String ID_REQUIRED = "module.dynamicfield.id.required";
		public static final String ID_EXISTS = "module.dynamicfield.id.exists";
		public static final String UUID_NOT_EXISTS = "module.dynamicfield.uuid.notexists";;
	}
	
	public static interface Cache {
		public static final String DYNAMIC_FIELD = "dynamicfield";
		public static final String DYNAMIC_FIELDS = "dynamicfields";
	}
}
