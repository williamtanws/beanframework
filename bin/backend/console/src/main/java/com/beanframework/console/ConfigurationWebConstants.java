package com.beanframework.console;

public interface ConfigurationWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.configuration.checkid}";
			public static final String PAGE = "${path.api.configuration.page}";
			public static final String HISTORY = "${path.api.configuration.history}";
		}

		public static final String CONFIGURATION = "${path.configuration}";
	}

	public interface View {
		public static final String LIST = "${view.configuration.list}";
	}

	public interface ModelAttribute {
		public static final String CREATE = "configurationCreate";
		public static final String UPDATE = "configurationUpdate";
		public static final String PROFILE = "configurationProfile";
	}
}
