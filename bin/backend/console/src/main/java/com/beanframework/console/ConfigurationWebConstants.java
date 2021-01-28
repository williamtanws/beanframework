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
		public static final String FORM = "${view.configuration.form}";
	}

	public interface ModelAttribute {
		public static final String CONFIGURATION_DTO = "configurationDto";
		public static final String PROFILE = "configurationProfile";
	}
	
	public interface ConfigurationPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "configuration_read";
		public static final String AUTHORITY_CREATE = "configuration_create";
		public static final String AUTHORITY_UPDATE = "configuration_update";
		public static final String AUTHORITY_DELETE = "configuration_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
