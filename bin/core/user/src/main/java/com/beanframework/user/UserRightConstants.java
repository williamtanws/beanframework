package com.beanframework.user;

public final class UserRightConstants {

	private UserRightConstants() {
		throw new AssertionError();
	}

	public interface Cache {
		public static final String ALL_USER_RIGHTS = "alluserrights";
	}

	public interface Table {
		public static final String USER_RIGHT = "userright";
		public static final String USER_RIGHT_FIELD = "userrightfield";

	}

	public interface Locale {
		public static final String ID_REQUIRED = "module.userright.id.required";
		public static final String ID_EXISTS = "module.userright.id.exists";
		public static final String UUID_NOT_EXISTS = "module.userright.uuid.notexists";;
	}
}
