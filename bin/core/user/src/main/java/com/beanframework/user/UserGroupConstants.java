package com.beanframework.user;

public final class UserGroupConstants {

	private UserGroupConstants() {
		throw new AssertionError();
	}

	public interface Cache {
		public static final String ALL_USER_GROUPS = "allusergroups";
	}

	public interface Table {
		public static final String USER_GROUP = "usergroup";
		public static final String USER_GROUP_FIELD = "usergroupfield";
		public static final String USER_GROUP_USER_GROUP_REL = "usergrouprel";

	}

	public interface Locale {
		public static final String ID_REQUIRED = "module.usergroup.id.required";
		public static final String ID_EXISTS = "module.usergroup.id.exists";
		public static final String UUID_NOT_EXISTS = "module.usergroup.uuid.notexists";;
	}
}
