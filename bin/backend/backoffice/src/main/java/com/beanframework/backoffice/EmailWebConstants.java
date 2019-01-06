package com.beanframework.backoffice;

public interface EmailWebConstants {

	public static final String LIST_SIZE = "${module.email.list.size}";

	public interface Path {
		public static final String EMAIL = "${path.email}";
	}

	public interface View {
		public static final String LIST = "${view.email.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "emailCreate";
		public static final String UPDATE = "emailUpdate";
		public static final String SEARCH = "emailSearch";
	}
}
