package com.beanframework.backoffice;

public interface MenuWebConstants {

	public static final String LIST_SIZE = "${module.menu.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.menu.checkid}";
			public static final String TREE = "${path.api.menu.tree}";
		}

		public static final String MENU = "${path.menu}";
	}

	public interface View {
		public static final String LIST = "${view.menu.list}";
	}

	public interface ModelAttribute {
		public static final String CREATE = "menuCreate";
		public static final String UPDATE = "menuUpdate";
		public static final String SEARCH = "menuSearch";
	}
}
