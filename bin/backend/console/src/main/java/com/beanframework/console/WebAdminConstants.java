package com.beanframework.console;

public interface WebAdminConstants {

	public static final String LIST_SIZE = "${module.admin.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.admin.checkid}";
		}

		public static final String PROFILE = "${path.admin.profile}";
		public static final String ADMIN = "${path.admin}";
	}

	public interface View {
		public static final String PROFILE = "${view.admin.profile}";
		public static final String LIST = "${view.admin.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "adminCreate";
		public static final String UPDATE = "adminUpdate";
		public static final String PROFILE = "adminProfile";
		public static final String SEARCH = "adminSearch";
	}
	
	public interface Locale {
		public static final String ACCOUNT_DISABLED = "module.admin.account.disabled";
		public static final String ACCOUNT_EXPIRED = "module.admin.account.expired";
		public static final String ACCOUNT_LOCKED = "module.admin.account.locked";
		public static final String ACCOUNT_PASSWORD_EXPIRED = "module.admin.account.password.expired";
	}
}
