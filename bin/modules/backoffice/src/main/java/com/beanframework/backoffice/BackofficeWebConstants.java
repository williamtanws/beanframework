package com.beanframework.backoffice;

public interface BackofficeWebConstants {

	public interface Configuration {
		public static final String DEFAULT_AVATAR = "${module.backoffice.configuration.avatar.default}";
	}

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
		public static final String REVISIONS = "revisions";
		public static final String FIELD_REVISIONS = "fieldRevisions";
		public static final String LOGIN_URL = "loginUrl";
	}

	public interface Path {
		public static final String BACKOFFICE = "${path.backoffice}";
		public static final String BACKOFFICE_API = "${backoffice.api}";
		public static final String LOGIN = "${path.backoffice.login}";
		public static final String LOGOUT = "${path.backoffice.logout}";
		public static final String DASHBOARD = "${path.backoffice.dashboard}";
	}

	public interface View {
		public static final String BACKOFFICE = "${view.backoffice}";
		public static final String LOGIN = "${view.backoffice.login}";
		public static final String DASHBOARD = "${view.backoffice.dashboard}";
	}

	public interface Authority {
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

	public interface Cookie {
		public static final String REMEMBER_ME = "REMEMBER_ME";
	}

	public interface Locale {
		public static final String SAVE_SUCCESS = "module.common.save.success";
		public static final String SAVE_FAIL = "module.common.save.fail";
		public static final String DELETE_SUCCESS = "module.common.delete.success";
		public static final String DELETE_FAIL = "module.common.delete.fail";
		public static final String RECORD_UUID_NOT_FOUND = "module.common.record.uuid.notfound";
	}
}
