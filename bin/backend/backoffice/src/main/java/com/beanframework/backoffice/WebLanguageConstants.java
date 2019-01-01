package com.beanframework.backoffice;

public interface WebLanguageConstants {

	public static final String LIST_SIZE = "${module.language.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.language.checkid}";
		}

		public static final String LANGUAGE = "${path.language}";
	}

	public interface View {
		public static final String LIST = "${view.language.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "languageCreate";
		public static final String UPDATE = "languageUpdate";
		public static final String SEARCH = "languageSearch";
	}
}
