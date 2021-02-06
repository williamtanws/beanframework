package com.beanframework.backoffice;

public interface LanguageWebConstants {

	public interface Path {

		public interface Api {
			public static final String LANGUAGE = "${path.api.language}";
			public static final String LANGUAGE_HISTORY = "${path.api.language.history}";
			public static final String LANGUAGE_CHECKID = "${path.api.language.checkid}";
		}

		public static final String LANGUAGE = "${path.language}";
		public static final String LANGUAGE_FORM = "${path.language.form}";
	}

	public interface View {
		public static final String LANGUAGE = "${view.language}";
		public static final String LANGUAGE_FORM = "${view.language.form}";
	}

	public interface ModelAttribute {
		public static final String LANGUAGE_DTO = "languageDto";
	}

	public interface LanguagePreAuthorizeEnum {
		public static final String AUTHORITY_READ = "language_read";
		public static final String AUTHORITY_CREATE = "language_create";
		public static final String AUTHORITY_UPDATE = "language_update";
		public static final String AUTHORITY_DELETE = "language_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
