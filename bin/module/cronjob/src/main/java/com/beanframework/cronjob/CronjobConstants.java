package com.beanframework.cronjob;

public final class CronjobConstants {

	public static final String PROPERTY_CONDITION_QUARTZ_ENABLED = "quartz.enabled";
	public static final String QUARTZ_TASK_ENABLED = "${quartz.task.enabled}";
	
	public static interface Query{
		public static final String SELECT_CRONJOB_BY_STARTUP_BY_STATUS = "select c from Cronjob c where c.startup = 0 and (c.status = 'Continued' or c.status = 'RUNNING' or c.status = 'PAUSED')";
	}

	public static interface Table {
		public static final String CRONJOB = "cronjob";
		public static final String CRONJOB_DATA = "cronjob_data";
	}

	public static interface Locale {

		public static final String CRONJOB_NAME_REQUIRED = "module.cronjob.name.required";
		public static final String CRONJOB_GROUP_REQUIRED = "module.cronjob.jobgroup.name.required";
		public static final String CRONJOB_NAME_GROUP_EXISTS = "module.cronjob.jobgroup.name.exists";
		public static final String CRONJOB_DATA_NAME_EXISTS = "module.cronjob.jobdata.name.exists";
		public static final String ID_REQUIRED = "module.cronjob.id.required";
		public static final String ID_EXISTS = "module.cronjob.id.exists";
	}
}
