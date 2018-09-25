package com.beanframework.backoffice;

public interface WebCustomerConstants {

	public static final String LIST_SIZE = "${module.customer.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.customer.checkid}";
		}

		public static final String CUSTOMER = "${path.customer}";
		public static final String PROFILE = "${path.customer.profile}";
	}

	public interface View {
		public static final String PROFILE = "${view.customer.profile}";
		public static final String LIST = "${view.customer.list}";
	}

	public interface ModelAttribute {
		public static final String CREATE = "customerCreate";
		public static final String UPDATE = "customerUpdate";
		public static final String PROFILE = "customerProfile";
		public static final String SEARCH = "customerSearch";
	}
	
	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('customer_read')";
		public static final String CREATE = "hasAuthority('customer_create')";
		public static final String UPDATE = "hasAuthority('customer_update')";
		public static final String DELETE = "hasAuthority('customer_delete')";
	}
}
