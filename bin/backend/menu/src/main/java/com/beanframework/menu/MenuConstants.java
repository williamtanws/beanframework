package com.beanframework.menu;

public interface MenuConstants {
	
	public interface Table{
		public static final String MENU = "menu";
		public static final String MENU_LANG = "menulang";
		
		public static final String MENU_USER_GROUP_REL = "menuusergrouprel";
	}

	public static interface Locale{
		public static final String UUID_NOT_EXISTS = "module.menu.uuid.notexists";
		public static final String ID_REQUIRED = "module.menu.id.required";
		public static final String ID_EXISTS = "module.menu.id.exists";
	}
	
	public static interface Cache{
		public static final String MENU = "menu";
		public static final String NAVIGATION_TREE = "navigationTree";
		public static final String NAVIGATION_TREE_BY_USERGROUP = "navigationTreeByUserGroup";
	}
}
