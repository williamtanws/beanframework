package com.beanframework.backoffice;

public interface TemplateWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.template.checkid}";
			public static final String PAGE = "${path.api.template.page}";
			public static final String HISTORY = "${path.api.template.history}";
		}

		public static final String TEMPLATE = "${path.template}";

	}

	public interface View {
		public static final String LIST = "${view.template.list}";
	}

	public interface ModelAttribute {
		public static final String TEMPLATE_DTO = "templateDto";
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
	}
	
	public interface Locale {
		public static final String SAVE_SUCCESS = "module.backoffice.save.success";
		public static final String SAVE_FAIL = "module.backoffice.save.fail";
		public static final String DELETE_SUCCESS = "module.backoffice.delete.success";
		public static final String DELETE_FAIL = "module.backoffice.delete.fail";
		public static final String RECORD_UUID_NOT_FOUND = "module.backoffice.record.uuid.notfound";
	}
}
