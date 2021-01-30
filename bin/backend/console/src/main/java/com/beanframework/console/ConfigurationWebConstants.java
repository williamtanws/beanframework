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

	public interface ConfigurationPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "configuration_read";
		public static final String AUTHORITY_CREATE = "configuration_create";
		public static final String AUTHORITY_UPDATE = "configuration_update";
		public static final String AUTHORITY_DELETE = "configuration_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_CREATE_UPDATE = HAS_CREATE + " or " + HAS_UPDATE;
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
