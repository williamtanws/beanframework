package com.beanframework.console;

public interface LicenseWebConstants extends ConsoleWebConstants {

	public static final String CONFIGURATION_ID_LICENSE_ACCEPTED = "platform.license.accepted";

	public interface Param {
		public static final String ACCEPT = "accept";
	}

	public interface Model {
		public static final String LICENSE = "license";
	}

	public interface ModelAttribute {
		public static final String LICENSE = "license";
	}

	public interface Path {
		public static final String LICENSE = "${path.license}";
	}

	public interface View {
		public static final String LICENSE = "${view.license}";
	}

	public interface Locale {
		public static final String ACCEPT_SUCCESS = "module.common.license.accepted";
	}
}
