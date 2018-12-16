package com.beanframework.cronjob;

public final class CronjobConstants {
	
	public static final String QUARTZ_PROPERTIES_LOCATION = "${quartz.properties.location}";
	public static final String QUARTZ_PROPERTIES_CLASSPATH = "${quartz.properties.classpath}";
	
	public static interface Table {
		public static final String CRONJOB = "cronjob";
		public static final String CRONJOB_DATA = "cronjobdata";
	}
	
	public static interface Locale {

		public static final String CRONJOB_NAME_REQUIRED = "module.cronjob.name.required";
		public static final String CRONJOB_GROUP_REQUIRED = "module.cronjob.jobgroup.name.required";
		public static final String CRONJOB_NAME_GROUP_EXISTS = "module.cronjob.jobgroup.name.exists";
		public static final String CRONJOB_DATA_NAME_EXISTS = "module.cronjob.jobdata.name.exists";
		public static final String ID_REQUIRED = "module.cronjob.id.required";
		public static final String ID_EXISTS = "module.cronjob.id.exists";
	}
	
	public static interface Cache {

		public static final String CRONJOB = "cronjob";
		public static final String CRONJOBS = "cronjobs";
	}
	

}
