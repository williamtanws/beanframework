package com.beanframework.backoffice;

public interface CurrencyWebConstants {

	public interface Path {

		public interface Api {
			public static final String CURRENCY = "${path.api.currency}";
			public static final String CURRENCY_HISTORY = "${path.api.currency.history}";
			public static final String CURRENCY_CHECKID = "${path.api.currency.checkid}";
		}

		public static final String CURRENCY = "${path.currency}";
		public static final String CURRENCY_FORM = "${path.currency.form}";
	}

	public interface View {
		public static final String CURRENCY = "${view.currency}";
		public static final String CURRENCY_FORM = "${view.currency.form}";
	}

	public interface ModelAttribute {
		public static final String CURRENCY_DTO = "currencyDto";
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
