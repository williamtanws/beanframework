package com.beanframework.user;

public final class UserConstants{
	
	private UserConstants() {
		throw new AssertionError();
	}
	
	public interface Path {

	}

	public interface View {

	}
	
	public interface Cache {
		public static final String ALL_USERS = "allusers";
		public static final String ALL_USER_GROUPS = "usergroups";
		public static final String ALL_USER_RIGHTS = "alluserrights";
	}
	
	public interface Table {
		public static final String USER = "user";
		public static final String USER_FIELD = "userfield";
		public static final String USER_GROUP = "usergroup";
		public static final String USER_PERMISSION = "userpermission";
		public static final String USER_RIGHT = "userright";
		public static final String USER_AUTHORITY = "userauthority";
		
		public static final String USER_GROUP_LANG = "usergrouplang";
		public static final String USER_RIGHT_LANG = "userrightlang";
		public static final String USER_PERMISSION_LANG = "userpermissionlang";

		public static final String USER_GROUP_REL = "usergrouprel";
		public static final String USER_GROUP_LANG_DYNAMIC_FIELD_REL = "usergroupdynrel";

	}
	
	public interface Locale {
		public interface User {
			public static final String ID_REQUIRED = "module.user.id.required";
			public static final String ID_EXISTS = "module.user.id.exists";
			public static final String UUID_NOT_EXISTS = "module.user.uuid.notexists";
			public static final String PASSWORD_REQUIRED = "module.user.password.required";;
		}
		
		public interface UserPermission {
			public static final String ID_REQUIRED = "module.userpermission.id.required";
			public static final String ID_EXISTS = "module.userpermission.id.exists";
			public static final String UUID_NOT_EXISTS = "module.userpermission.uuid.notexists";;
		}
		
		public interface UserRight {
			public static final String ID_REQUIRED = "module.userright.id.required";
			public static final String ID_EXISTS = "module.userright.id.exists";
			public static final String UUID_NOT_EXISTS = "module.userright.uuid.notexists";;
		}
		
		public interface UserGroup {
			public static final String ID_REQUIRED = "module.usergroup.id.required";
			public static final String ID_EXISTS = "module.usergroup.id.exists";
			public static final String UUID_NOT_EXISTS = "module.usergroup.uuid.notexists";;
		}
	}
	
	public static final String MAX_SESSION_USER = "${max.session.user:1}";
	public static final String MAX_SESSION_PREVENTS_LOGIN = "${max.session.prevents.login:1}";
}
