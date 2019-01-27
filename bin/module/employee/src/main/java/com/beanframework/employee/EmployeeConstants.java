package com.beanframework.employee;

public interface EmployeeConstants {
	
	public static final String PROFILE_PICTURE_LOCATION = "${module.employee.profile.picture.location}";
	
	public interface Discriminator{
		public static final String EMPLOYEE = "employee";
	}

	public static interface Authority {
		public static final String EMPLOYEE_READ = "EMPLOYEE_READ";
		public static final String EMPLOYEE_CREATE = "EMPLOYEE_CREATE";
		public static final String EMPLOYEE_UPDATE = "EMPLOYEE_UPDATE";
		public static final String EMPLOYEE_DELETE = "EMPLOYEE_DELETE";
	}

	public static interface Locale{
		public static final String ID_REQUIRED = "module.employee.id.required";
		public static final String ID_EXISTS = "module.employee.id.exists";
		public static final String ID_NOT_EXISTS = "module.employee.id.notexists";
		public static final String UUID_NOT_EXISTS = "module.employee.uuid.notexists";
		public static final String PASSWORD_REQUIRED = "module.employee.password.required";
		public static final String SAVE_CURRENT_ADMIN_ERROR = "module.employee.current.save.error";
		public static final String NAME_REQUIRED = "module.employee.name.required";
		public static final String PICTURE_WRONGFORMAT = "module.employee.picture.wrongformat";
	}

}
