package com.beanframework.backoffice;

public interface MenuWebConstants {

	public interface Path {

		public interface Api {
			public static final String MENU = "${path.api.menu}";
			public static final String MENU_HISTORY = "${path.api.menu.history}";
			public static final String MENU_CHECKID = "${path.api.menu.checkid}";
			public static final String MENU_TREE = "${path.api.menu.tree}";
		}

		public static final String MENU = "${path.menu}";
		public static final String MENU_FORM = "${path.menu.form}";
	}

	public interface View {
		public static final String MENU = "${view.menu}";
		public static final String MENU_FORM = "${view.menu.form}";
	}

	public interface ModelAttribute {
		public static final String MENU_DTO = "menuDto";
	}

	public static interface MenuPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "menu_read";
		public static final String AUTHORITY_CREATE = "menu_create";
		public static final String AUTHORITY_UPDATE = "menu_update";
		public static final String AUTHORITY_DELETE = "menu_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
