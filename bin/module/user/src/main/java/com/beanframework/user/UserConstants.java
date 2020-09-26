package com.beanframework.user;

public final class UserConstants {

	private UserConstants() {
		throw new AssertionError();
	}
	
	public interface Query {
		public static final String SELECT_USER_BY_USER_GROUP = "SELECT u FROM User u LEFT JOIN u.userGroups g WHERE g = :uuid";
	}

	public interface Table {
		public static final String USER = "user";
		public static final String USER_FIELD = "user_field";
		public static final String USER_AUTHORITY = "user_authority";
		public static final String USER_USER_GROUP_REL = "user_usergrouprel";
		public static final String USER_COMPANY_REL = "user_companyrel";
		public static final String USER_ADDRESS_REL = "user_addressrel";
	}

	public interface Locale {
		public static final String ID_REQUIRED = "module.user.id.required";
		public static final String ID_EXISTS = "module.user.id.exists";
		public static final String UUID_NOT_EXISTS = "module.user.uuid.notexists";
		public static final String PASSWORD_REQUIRED = "module.user.password.required";
	}

	public static final String MAX_SESSION_USER = "${module.user.session.max:-1}";
	public static final String MAX_SESSION_PREVENTS_LOGIN = "${module.user.session.prevents.login:false}";
	public static final String USER_MEDIA_LOCATION = "${module.user.media.location}";
	public static final String PATH_USER_PROFILE_PICTURE = "${path.user.profile.picture}";
	public static final String USER_PROFILE_PICTURE_THUMBNAIL_WIDTH = "${module.user.profile.picture.thumbnail.width}";
	public static final String USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT = "${module.user.profile.picture.thumbnail.height}";
}
