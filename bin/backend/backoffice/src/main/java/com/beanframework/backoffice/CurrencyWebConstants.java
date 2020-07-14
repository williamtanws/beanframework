package com.beanframework.backoffice;

public interface CurrencyWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.currency.checkid}";
			public static final String PAGE = "${path.api.currency.page}";
			public static final String HISTORY = "${path.api.currency.history}";
		}

		public static final String COMMENT = "${path.currency}";
	}

	public interface View {
		public static final String LIST = "${view.currency.list}";
	}

	public interface ModelAttribute {

		public static final String COMMENT_DTO = "currencyDto";
	}
}
