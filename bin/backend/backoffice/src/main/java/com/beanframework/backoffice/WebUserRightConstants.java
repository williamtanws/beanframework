package com.beanframework.backoffice;

public interface WebUserRightConstants {

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
	
	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('userright_read')";
		public static final String CREATE = "hasAuthority('userright_create')";
		public static final String UPDATE = "hasAuthority('userright_update')";
		public static final String DELETE = "hasAuthority('userright_delete')";
	}
}
