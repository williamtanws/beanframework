package com.beanframework.backoffice;

public interface CustomerWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.customer.checkid}";
			public static final String PAGE = "${path.api.customer}";
			public static final String HISTORY = "${path.api.customer.history}";
		}

		public static final String CUSTOMER = "${path.customer}";
		public static final String PROFILE = "${path.customer.profile}";
	}

	public interface View {
		public static final String PROFILE = "${view.customer.profile}";
		public static final String LIST = "${view.customer}";
	}

	public interface ModelAttribute {
		public static final String CUSTOMER_DTO = "customerDto";
		public static final String PROFILE = "customerProfile";
	}
	
	public interface CustomerPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "customer_read";
		public static final String AUTHORITY_CREATE = "customer_create";
		public static final String AUTHORITY_UPDATE = "customer_update";
		public static final String AUTHORITY_DELETE = "customer_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
