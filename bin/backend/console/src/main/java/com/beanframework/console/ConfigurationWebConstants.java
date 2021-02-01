package com.beanframework.console;

public interface ConfigurationWebConstants {

	public interface Path {

		public interface Api {
			public static final String CONFIGURATION_PAGE = "${path.api.configuration.page}";
			public static final String CONFIGURATION_PAGE_HISTORY = "${path.api.configuration.page.history}";
			public static final String CONFIGURATION_CHECKID = "${path.api.configuration.checkid}";
		}

		public static final String CONFIGURATION_PAGE = "${path.configuration.page}";
		public static final String CONFIGURATION_FORM = "${path.configuration.form}";
	}

	public interface View {
		public static final String PAGE = "${view.configuration.page}";
		public static final String FORM = "${view.configuration.form}";
	}

	public interface ModelAttribute {
		public static final String CONFIGURATION_DTO = "configurationDto";
	}
}
