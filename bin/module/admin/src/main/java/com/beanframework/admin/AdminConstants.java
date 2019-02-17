package com.beanframework.admin;

public interface AdminConstants {

	public interface Discriminator {
		public static final String ADMIN = "admin";
	}

	public static interface Admin {
		public static final String DEFAULT_ID = "${module.admin.default.id}";
		public static final String DEFAULT_PASSWORD = "${module.admin.default.password}";
	}

	public static interface Locale {
		public static final String ID_REQUIRED = "module.admin.id.required";
		public static final String ID_EXISTS = "module.admin.id.exists";
		public static final String UUID_NOT_EXISTS = "module.admin.uuid.notexists";
		public static final String PASSWORD_REQUIRED = "module.admin.password.required";
		public static final String SAVE_CURRENT_ADMIN_ERROR = "module.admin.current.save.error";
	}

}
