package com.beanframework.console;

public interface ApplicationWebConstants {

	public static final String PAGINATION = "pagination";

	public interface Pagination {
		public static final String PAGE = "page";
		public static final String SIZE = "size";
		public static final String DIRECTION = "direction";
		public static final String PROPERTIES = "properties";
		public static final String PROPERTIES_SPLIT = ",";
	}

	public interface Param {
		public static final String UUID = "uuid";
		public static final String ID = "id";
	}

	public interface Model {
		public static final String ERROR = "error";
		public static final String SUCCESS = "success";
		public static final String INFO = "info";
		public static final String PAGINATION = "pagination";
	}

	public interface Path {
		public static final String APPLICATION_OVERVIEW = "${path.console.application.overview}";
		public static final String APPLICATION_METRICS = "${path.console.application.metrics}";
		public static final String APPLICATION_ENVIRONMENT = "${path.console.application.environment}";
		public static final String APPLICATION_LOGFILE = "${path.console.application.logfile}";
		public static final String APPLICATION_LOGGERS = "${path.console.application.loggers}";
		public static final String APPLICATION_THREADS = "${path.console.application.threads}";
		public static final String APPLICATION_HTTPTRACES = "${path.console.application.httptraces}";
		public static final String APPLICATION_AUDITLOG = "${path.console.application.auditlog}";
		public static final String APPLICATION_HEAPDUMP = "${path.console.application.heapdump}";
	}

	public interface View {
		public static final String APPLICATION_OVERVIEW = "${view.console.application.overview}";
		public static final String APPLICATION_METRICS = "${view.console.application.metrics}";
		public static final String APPLICATION_ENVIRONMENT = "${view.console.application.environment}";
		public static final String APPLICATION_LOGFILE = "${view.console.application.logfile}";
		public static final String APPLICATION_LOGGERS = "${view.console.application.loggers}";
		public static final String APPLICATION_THREADS = "${view.console.application.threads}";
		public static final String APPLICATION_HTTPTRACES = "${view.console.application.httptraces}";
		public static final String APPLICATION_AUDITLOG = "${view.console.application.auditlog}";
		public static final String APPLICATION_HEAPDUMP = "${view.console.application.heapdump}";
	}

	public interface Locale {
		public static final String SAVE_SUCCESS = "module.console.save.success";
		public static final String SAVE_FAIL = "module.console.save.fail";
		public static final String DELETE_SUCCESS = "module.console.delete.success";
		public static final String DELETE_FAIL = "module.console.delete.fail";
		public static final String LOGIN_WRONG_USERNAME_PASSWORD = "module.console.login.error.wrongusernameorpassword";
		public static final String RECORD_UUID_NOT_FOUND = "module.console.record.uuid.notfound";
	}
}
