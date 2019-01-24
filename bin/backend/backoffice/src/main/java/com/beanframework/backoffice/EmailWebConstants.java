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
		public static final String LIST = "${view.email.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "emailCreate";
		public static final String UPDATE = "emailUpdate";
	}
}
