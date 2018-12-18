package com.beanframework.user;

public final class UserAuthorityConstants {

	private UserAuthorityConstants() {
		throw new AssertionError();
	}

	public interface Locale {
		public static final String ID_REQUIRED = "module.userauthority.id.required";
		public static final String ID_EXISTS = "module.userauthority.id.exists";
		public static final String UUID_NOT_EXISTS = "module.userauthority.uuid.notexists";;
	}
}
