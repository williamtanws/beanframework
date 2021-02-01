package com.beanframework.backoffice;

public interface UserPermissionWebConstants {

	public interface Path {

		public interface Api {
			public static final String USERPERMISSION_PAGE = "${path.api.userpermission.page}";
			public static final String USERPERMISSION_PAGE_HISTORY = "${path.api.userpermission.page.history}";
			public static final String USERPERMISSION_CHECKID = "${path.api.userpermission.checkid}";
		}

		public static final String USERPERMISSION_PAGE = "${path.userpermission.page}";
		public static final String USERPERMISSION_FORM = "${path.userpermission.form}";
	}

	public interface View {
		public static final String PAGE = "${view.userpermission.page}";
		public static final String FORM = "${view.userpermission.form}";
	}

	public interface ModelAttribute {
		public static final String USERPERMISSION_DTO = "userpermissionDto";
	}
	
	public interface UserPermissionPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "userpermission_read";
		public static final String AUTHORITY_CREATE = "userpermission_create";
		public static final String AUTHORITY_UPDATE = "userpermission_update";
		public static final String AUTHORITY_DELETE = "userpermission_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
