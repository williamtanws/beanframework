package com.beanframework.backoffice;

public interface CustomerWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.customer.checkid}";
			public static final String PAGE = "${path.api.customer.page}";
			public static final String HISTORY = "${path.api.customer.history}";
		}

		public static final String CUSTOMER = "${path.customer}";
		public static final String PROFILE = "${path.customer.profile}";
	}

	public interface View {
		public static final String PROFILE = "${view.customer.profile}";
		public static final String LIST = "${view.customer.list}";
	}

	public interface ModelAttribute {
		public static final String CUSTOMER_DTO = "customerDto";
		public static final String PROFILE = "customerProfile";
	}
}
