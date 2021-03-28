package com.beanframework.backoffice;

public interface UserGroupWebConstants {

	public interface Path {

		public interface Api {
			public static final String USERGROUP = "${path.api.usergroup}";
			public static final String USERGROUP_HISTORY = "${path.api.usergroup.history}";
			public static final String USERGROUP_CHECKID = "${path.api.usergroup.checkid}";
		}

		public static final String USERGROUP = "${path.usergroup}";
		public static final String USERGROUP_FORM = "${path.usergroup.form}";
	}

	public interface View {
		public static final String USERGROUP = "${view.usergroup}";
		public static final String USERGROUP_FORM = "${view.usergroup.form}";
	}

	public interface ModelAttribute {
		public static final String USERGROUP_DTO = "usergroupDto";
	}

	public interface UserGroupPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "usergroup_read";
		public static final String AUTHORITY_CREATE = "usergroup_create";
		public static final String AUTHORITY_UPDATE = "usergroup_update";
		public static final String AUTHORITY_DELETE = "usergroup_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
