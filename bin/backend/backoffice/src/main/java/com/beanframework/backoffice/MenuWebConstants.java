package com.beanframework.backoffice;

public interface MenuWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.menu.checkid}";
			public static final String TREE = "${path.api.menu.tree}";
			public static final String PAGE = "${path.api.menu.page}";
			public static final String HISTORY = "${path.api.menu.history}";
		}

		public static final String MENU = "${path.menu}";
	}

	public interface View {
		public static final String LIST = "${view.menu.list}";
	}

	public interface ModelAttribute {
		public static final String CREATE = "menuCreate";
		public static final String UPDATE = "menuUpdate";
	}
}
