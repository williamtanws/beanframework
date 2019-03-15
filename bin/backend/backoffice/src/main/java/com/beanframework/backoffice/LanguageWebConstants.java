package com.beanframework.backoffice;

public interface LanguageWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.language.checkid}";
			public static final String PAGE = "${path.api.language.page}";
			public static final String HISTORY = "${path.api.language.history}";
		}

		public static final String LANGUAGE = "${path.language}";
	}

	public interface View {
		public static final String LIST = "${view.language.list}";
	}

	public interface ModelAttribute {
		public static final String LANGUAGE_DTO = "languageDto";
	}
}
