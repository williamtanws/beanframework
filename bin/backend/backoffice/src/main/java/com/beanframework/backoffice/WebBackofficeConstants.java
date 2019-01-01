package com.beanframework.backoffice;

public interface WebBackofficeConstants {
	
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
		public static final String MENU_NAVIGATION = "menuNavigation";
		public static final String MODULE_LANGUAGES = "moduleLanguages";
	}

	public interface Path {
		public static final String BACKOFFICE = "${backoffice.webroot}";
		public static final String BACKOFFICE_API = "${backoffice.api}";
		public static final String LOGIN = "${path.backoffice.login}";
		public static final String LOGOUT = "${path.backoffice.logout}";
		public static final String DASHBOARD = "${path.backoffice.dashboard}";
	}

	public interface View {
		public static final String LOGIN = "${view.backoffice.login}";
		public static final String DASHBOARD = "${view.backoffice.dashboard}";
	}

	public interface Authority {
		public static final String BACKOFFICE = "${module.backoffice.authority}";
		public static final String ESCAPE = "${module.backoffice.authority.escape}";
	}

	public interface Http {
		public static final String USERNAME_PARAM = "${module.backoffice.http.username.param}";
		public static final String PASSWORD_PARAM = "${module.backoffice.http.password.param}";
		public static final String ANTPATTERNS_PERMITALL = "${module.backoffice.http.antPatterns.permitAll}";
		public static final String REMEMBERME_PARAM = "${module.backoffice.http.rememberme.cookiename}";
		public static final String REMEMBERME_COOKIENAME = "${module.backoffice.http.rememberme.param}";
		public static final String REMEMBERME_TOKENVALIDITYSECONDS = "${module.backoffice.http.rememberme.tokenvalidityseconds}";

	}
	
	public interface Cookie{
		public static final String REMEMBER_ME = "REMEMBER_ME";
	}

	public interface Locale {
		public static final String SAVE_SUCCESS = "module.backoffice.save.success";
		public static final String SAVE_FAIL = "module.backoffice.save.fail";
		public static final String DELETE_SUCCESS = "module.backoffice.delete.success";
		public static final String DELETE_FAIL = "module.backoffice.delete.fail";
		public static final String RECORD_UUID_NOT_FOUND = "module.backoffice.record.uuid.notfound";
		public static final String LOGIN_WRONG_USERNAME_PASSWORD = "module.backoffice.login.error.wrongusernameorpassword";
	}
}
