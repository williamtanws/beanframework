package com.beanframework.backoffice;

public interface WebUserGroupConstants {

	public static final String LIST_SIZE = "${module.usergroup.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.usergroup.checkid}";
		}

		public static final String USERGROUP = "${path.usergroup}";
	}

	public interface View {
		public static final String LIST = "${view.usergroup.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "usergroupCreate";
		public static final String UPDATE = "usergroupUpdate";
		public static final String SEARCH = "usergroupSearch";
	}
	
	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('usergroup_read')";
		public static final String CREATE = "hasAuthority('usergroup_create')";
		public static final String UPDATE = "hasAuthority('usergroup_update')";
		public static final String DELETE = "hasAuthority('usergroup_delete')";
	}
}
