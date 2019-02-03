package com.beanframework.backoffice;

public interface UserPermissionWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.userpermission.checkid}";
			public static final String PAGE = "${path.api.userpermission.page}";
			public static final String HISTORY = "${path.api.userpermission.history}";
		}

		public static final String USERPERMISSION = "${path.userpermission}";
	}

	public interface View {
		public static final String LIST = "${view.userpermission.list}";
	}

	public interface ModelAttribute {
		public static final String USERPERMISSION_DTO = "userpermissionDto";
	}
}
