package com.beanframework.user;

public interface VendorConstants {

	public interface Discriminator {
		public static final String VENDOR = "vendor";
	}

	public static interface Locale {
		public static final String ID_REQUIRED = "module.vendor.id.required";
		public static final String ID_EXISTS = "module.vendor.id.exists";
		public static final String UUID_NOT_EXISTS = "module.vendor.uuid.notexists";
		public static final String ID_NOT_EXISTS = "module.vendor.id.notexists";
		public static final String PASSWORD_REQUIRED = "module.vendor.password.required";
		public static final String SAVE_CURRENT_VENDOR_ERROR = "module.vendor.current.save.error";
	}

	public static final String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE = "vendor.dynamicfield.template";
}
