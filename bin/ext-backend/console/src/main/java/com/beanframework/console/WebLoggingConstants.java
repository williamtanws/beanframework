package com.beanframework.console;

public class WebLoggingConstants {

	public interface Location {
		public static final String LOGGING = "${log.dir}";
	}

	public interface Path {
		public static final String LOGGING = "${path.logging}";
		public static final String LOGGING_TAIL = "${path.logging.tail}";
		public static final String LOGGING_DOWNLOAD = "${path.logging.download}";
		public static final String LOGGING_LEVEL = "${path.logging.level}";
	}

	public interface View {
		public static final String LOGGING_LEVEL = "${view.logging.level}";
		public static final String LOGGING_TAIL = "${view.logging.tail}";
	}
}
