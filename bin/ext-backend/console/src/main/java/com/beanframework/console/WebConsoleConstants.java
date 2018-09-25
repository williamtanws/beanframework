package com.beanframework.console;

public interface WebConsoleConstants {

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
		public static final String CONSOLE = "${console.webroot}";
		public static final String CONSOLE_API = "${console.api}";
		public static final String LOGIN = "${path.console.login}";
		public static final String LOGOUT = "${path.console.logout}";
		public static final String DASHBOARD = "${path.console.dashboard}";
	}

	public interface View {
		public static final String LOGIN = "${view.console.login}";
		public static final String DASHBOARD = "${view.console.dashboard}";
	}
	
	public interface Authority {
		public static final String CONSOLE = "${module.console.permission.access}";
	}

	public interface Http {
		public static final String USERNAME_PARAM = "${module.console.http.username.param}";
		public static final String PASSWORD_PARAM = "${module.console.http.password.param}";
		public static final String ANTPATTERNS_PERMITALL = "${module.console.http.antPatterns.permitAll}";
		public static final String REMEMBERME_PARAM = "${module.console.http.rememberme.cookiename}";
		public static final String REMEMBERME_COOKIENAME = "${module.console.http.rememberme.param}";
		public static final String REMEMBERME_TOKENVALIDITYSECONDS = "${module.console.http.rememberme.tokenvalidityseconds}";

	}

	public interface Cookie {
		public static final String REMEMBER_ME = "REMEMBER_ME";
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
