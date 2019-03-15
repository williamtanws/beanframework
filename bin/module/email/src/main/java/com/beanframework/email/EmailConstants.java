package com.beanframework.email;

public class EmailConstants {

	public static interface Table {
		public static final String EMAIL = "email";
	}

	public static interface Locale {
		public static final String UUID_NOT_EXISTS = "module.email.uuid.notexists";;
	}

	public static final String EMAIL_ATTACHMENT_LOCATION = "${module.email.attachment.location}";
}
