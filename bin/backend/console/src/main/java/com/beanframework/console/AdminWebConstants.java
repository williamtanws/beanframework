package com.beanframework.console;

public interface AdminWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.admin.checkid}";
			public static final String PAGE = "${path.api.admin.page}";
			public static final String HISTORY = "${path.api.admin.history}";
		}

		public static final String PROFILE = "${path.admin.profile}";
		public static final String ADMIN = "${path.admin}";
	}

	public interface View {
		public static final String PROFILE = "${view.admin.profile}";
		public static final String LIST = "${view.admin.list}";
	}

	public interface ModelAttribute {

		public static final String ADMIN_DTO = "adminDto";
		public static final String PROFILE = "adminProfile";
	}

	public interface Locale {
		public static final String ACCOUNT_DISABLED = "module.admin.account.disabled";
		public static final String ACCOUNT_EXPIRED = "module.admin.account.expired";
		public static final String ACCOUNT_LOCKED = "module.admin.account.locked";
		public static final String ACCOUNT_PASSWORD_EXPIRED = "module.admin.account.password.expired";
	}
	
	public interface AdminPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "admin_read";
		public static final String AUTHORITY_CREATE = "admin_create";
		public static final String AUTHORITY_UPDATE = "admin_update";
		public static final String AUTHORITY_DELETE = "admin_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
