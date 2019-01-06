package com.beanframework.backoffice;

public interface UserRightWebConstants {

	public static final String LIST_SIZE = "${module.userright.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.userright.checkid}";
		}

		public static final String USERRIGHT = "${path.userright}";
	}

	public interface View {
		public static final String LIST = "${view.userright.list}";
	}

	public interface ModelAttribute {
		public static final String CREATE = "userrightCreate";
		public static final String UPDATE = "userrightUpdate";
		public static final String SEARCH = "userrightSearch";
	}
}
