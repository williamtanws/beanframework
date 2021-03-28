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
		public static final String LIST = "${view.country}";
	}

	public interface ModelAttribute {

		public static final String COMMENT_DTO = "countryDto";
	}
	
	public interface CountryPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "country_read";
		public static final String AUTHORITY_CREATE = "country_create";
		public static final String AUTHORITY_UPDATE = "country_update";
		public static final String AUTHORITY_DELETE = "country_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
