package com.beanframework.backoffice;

public interface CustomerWebConstants {

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
}
