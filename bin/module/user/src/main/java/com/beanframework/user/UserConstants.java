package com.beanframework.user;

public final class UserConstants {

	private UserConstants() {
		throw new AssertionError();
	}

	public interface Table {
		public static final String USER = "user";
		public static final String USER_FIELD = "userfield";
		public static final String USER_AUTHORITY = "userauthority";

		public static final String USER_USER_GROUP_REL = "userusergrouprel";
		public static final String USER_GROUP_LANG_DYNAMIC_FIELD_REL = "usergroupdynrel";

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
