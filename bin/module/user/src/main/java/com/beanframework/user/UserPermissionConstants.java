package com.beanframework.user;

public final class UserPermissionConstants {

	private UserPermissionConstants() {
		throw new AssertionError();
	}

	public interface Table {
		public static final String USER_PERMISSION = "userpermission";
		public static final String USER_PERMISSION_FIELD = "userpermissionfield";

	}

	public interface Locale {
		public static final String ID_REQUIRED = "module.userpermission.id.required";
		public static final String ID_EXISTS = "module.userpermission.id.exists";
		public static final String UUID_NOT_EXISTS = "module.userpermission.uuid.notexists";;
	}
}
