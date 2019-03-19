package com.beanframework.employee;

public interface EmployeeConstants {

	public interface Discriminator {
		public static final String EMPLOYEE = "employee";
	}

	public static interface Authority {
		public static final String EMPLOYEE_READ = "EMPLOYEE_READ";
		public static final String EMPLOYEE_CREATE = "EMPLOYEE_CREATE";
		public static final String EMPLOYEE_UPDATE = "EMPLOYEE_UPDATE";
		public static final String EMPLOYEE_DELETE = "EMPLOYEE_DELETE";
	}

	public static interface Locale {
		public static final String ID_REQUIRED = "module.employee.id.required";
		public static final String ID_EXISTS = "module.employee.id.exists";
		public static final String ID_NOT_EXISTS = "module.employee.id.notexists";
		public static final String UUID_NOT_EXISTS = "module.employee.uuid.notexists";
		public static final String PASSWORD_REQUIRED = "module.employee.password.required";
		public static final String SAVE_CURRENT_ADMIN_ERROR = "module.employee.current.save.error";
		public static final String NAME_REQUIRED = "module.employee.name.required";
		public static final String PICTURE_WRONGFORMAT = "module.employee.picture.wrongformat";
	}

	public static final String EMPLOYEE_MEDIA_LOCATION = "${module.employee.media.location}";
	public static final String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE = "module.employee.dynamicfield.template";
	public static final String EMPLOYEE_PROFILE_PICTURE_THUMBNAIL_WIDTH = "${module.employee.profile.picture.thumbnail.width}";
	public static final String EMPLOYEE_PROFILE_PICTURE_THUMBNAIL_HEIGHT = "${module.employee.profile.picture.thumbnail.height}";

}
