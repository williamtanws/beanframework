package com.beanframework.console;

public interface ConfigurationWebConstants {

	public static final String LIST_SIZE = "${module.configuration.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.configuration.checkid}";
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
		public static final String SEARCH = "configurationSearch";
	}
}
