package com.beanframework.backoffice;

public interface UserPermissionWebConstants {

	public static final String LIST_SIZE = "${module.userpermission.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.userpermission.checkid}";
		}

		public static final String USERPERMISSION = "${path.userpermission}";
	}

	public interface View {
		public static final String LIST = "${view.userpermission.list}";
	}

	public interface ModelAttribute {
		public static final String CREATE = "userpermissionCreate";
		public static final String UPDATE = "userpermissionUpdate";
		public static final String SEARCH = "userpermissionSearch";
	}
}
