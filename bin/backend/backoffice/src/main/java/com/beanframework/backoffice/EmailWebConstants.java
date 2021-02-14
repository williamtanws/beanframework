package com.beanframework.backoffice;

public interface EmailWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.email.checkid}";
			public static final String PAGE = "${path.api.email.page}";
			public static final String HISTORY = "${path.api.email.history}";
		}

		public static final String EMAIL = "${path.email}";

	}

	public interface View {
		public static final String LIST = "${view.email}";
	}

	public interface ModelAttribute {
		public static final String EMAIL_DTO = "emailDto";
	}

	public static interface EmailPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "email_read";
		public static final String AUTHORITY_CREATE = "email_create";
		public static final String AUTHORITY_UPDATE = "email_update";
		public static final String AUTHORITY_DELETE = "email_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
