package com.beanframework.backoffice;

public interface CountryWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.country.checkid}";
			public static final String PAGE = "${path.api.country.page}";
			public static final String HISTORY = "${path.api.country.history}";
		}

		public static final String COMMENT = "${path.country}";
	}

	public interface View {
		public static final String LIST = "${view.country.list}";
	}

	public interface ModelAttribute {

		public static final String COMMENT_DTO = "countryDto";
	}
}
