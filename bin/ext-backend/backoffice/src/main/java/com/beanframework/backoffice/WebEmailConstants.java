package com.beanframework.backoffice;

public interface WebEmailConstants {

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
	
	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('email_read')";
		public static final String CREATE = "hasAuthority('email_create')";
		public static final String UPDATE = "hasAuthority('email_update')";
		public static final String DELETE = "hasAuthority('email_delete')";
	}
}
