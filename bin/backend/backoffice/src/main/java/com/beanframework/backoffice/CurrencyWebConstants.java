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
	
	public interface CurrencyPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "currency_read";
		public static final String AUTHORITY_CREATE = "currency_create";
		public static final String AUTHORITY_UPDATE = "currency_update";
		public static final String AUTHORITY_DELETE = "currency_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
