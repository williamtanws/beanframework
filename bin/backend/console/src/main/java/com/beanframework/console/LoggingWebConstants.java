package com.beanframework.console;

public class LoggingWebConstants {

	public interface Location {
		public static final String LOGGING = "${log.dir}";
	}

	public interface Path {
		
		public interface Api {
			public static final String LOGGING_TAIL = "${path.api.logging.tail}";
			public static final String LOGGING_LEVEL = "${path.api.logging.level}";
		}
		
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
