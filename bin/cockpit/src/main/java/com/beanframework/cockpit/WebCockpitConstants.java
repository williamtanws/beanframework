package com.beanframework.cockpit;

public interface WebCockpitConstants {
	
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
		public static final String COCKPIT = "${cockpit.webroot}";
		public static final String COCKPIT_API = "${cockpit.api}";
		public static final String LOGIN = "${path.cockpit.login}";
		public static final String LOGOUT = "${path.cockpit.logout}";
		public static final String DASHBOARD = "${path.cockpit.dashboard}";
	}

	public interface View {
		public static final String LOGIN = "${view.cockpit.login}";
		public static final String DASHBOARD = "${view.cockpit.dashboard}";
	}

	public interface Authority {
		public static final String COCKPIT = "${module.cockpit.authority}";
		public static final String ESCAPE = "${module.cockpit.authority.escape}";
	}

	public interface Http {
		public static final String USERNAME_PARAM = "${module.cockpit.http.username.param}";
		public static final String PASSWORD_PARAM = "${module.cockpit.http.password.param}";
		public static final String ANTPATTERNS_PERMITALL = "${module.cockpit.http.antPatterns.permitAll}";
		public static final String REMEMBERME_PARAM = "${module.cockpit.http.rememberme.cookiename}";
		public static final String REMEMBERME_COOKIENAME = "${module.cockpit.http.rememberme.param}";
		public static final String REMEMBERME_TOKENVALIDITYSECONDS = "${module.cockpit.http.rememberme.tokenvalidityseconds}";

	}
	
	public interface Cookie{
		public static final String REMEMBER_ME = "REMEMBER_ME";
	}

	public interface Locale {
		public static final String SAVE_SUCCESS = "module.cockpit.save.success";
		public static final String SAVE_FAIL = "module.cockpit.save.fail";
		public static final String DELETE_SUCCESS = "module.cockpit.delete.success";
		public static final String DELETE_FAIL = "module.cockpit.delete.fail";
		public static final String RECORD_UUID_NOT_FOUND = "module.cockpit.record.uuid.notfound";
		public static final String LOGIN_WRONG_USERNAME_PASSWORD = "module.cockpit.login.error.wrongusernameorpassword";
	}
}
