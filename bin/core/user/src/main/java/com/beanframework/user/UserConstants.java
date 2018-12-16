package com.beanframework.user;

public final class UserConstants {

	private UserConstants() {
		throw new AssertionError();
	}

	public interface Cache {
		public static final String ALL_USERS = "allusers";
	}

	public interface Table {
		public static final String USER = "user";
		public static final String USER_FIELD = "userfield";
		public static final String USER_AUTHORITY = "userauthority";

		public static final String USER_GROUP_REL = "usergrouprel";
		public static final String USER_GROUP_LANG_DYNAMIC_FIELD_REL = "usergroupdynrel";

	}

	public interface Locale {
		public static final String ID_REQUIRED = "module.user.id.required";
		public static final String ID_EXISTS = "module.user.id.exists";
		public static final String UUID_NOT_EXISTS = "module.user.uuid.notexists";
		public static final String PASSWORD_REQUIRED = "module.user.password.required";
	}

	public static final String MAX_SESSION_USER = "${max.session.user:1}";
	public static final String MAX_SESSION_PREVENTS_LOGIN = "${max.session.prevents.login:1}";
}
