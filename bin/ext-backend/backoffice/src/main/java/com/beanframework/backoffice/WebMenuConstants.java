package com.beanframework.backoffice;

public interface WebMenuConstants {

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
	
	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('menu_read')";
		public static final String CREATE = "hasAuthority('menu_create')";
		public static final String UPDATE = "hasAuthority('menu_update')";
		public static final String DELETE = "hasAuthority('menu_delete')";
	}
}
