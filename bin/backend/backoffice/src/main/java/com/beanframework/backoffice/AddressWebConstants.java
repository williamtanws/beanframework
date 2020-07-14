package com.beanframework.backoffice;

public interface AddressWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.address.checkid}";
			public static final String PAGE = "${path.api.address.page}";
			public static final String HISTORY = "${path.api.address.history}";
		}

		public static final String COMMENT = "${path.address}";
	}

	public interface View {
		public static final String LIST = "${view.address.list}";
	}

	public interface ModelAttribute {

		public static final String COMMENT_DTO = "addressDto";
	}
}
