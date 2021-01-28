package com.beanframework.backoffice;

public interface DynamicFieldTemplateWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.dynamicfieldtemplate.checkid}";
			public static final String PAGE = "${path.api.dynamicfieldtemplate.page}";
			public static final String HISTORY = "${path.api.dynamicfieldtemplate.history}";
		}

		public static final String DYNAMICFIELDTEMPLATE = "${path.dynamicfieldtemplate}";
	}

	public interface View {
		public static final String LIST = "${view.dynamicfieldtemplate.list}";
	}

	public interface ModelAttribute {
		public static final String DYNAMICFIELDTEMPLATE_DTO = "dynamicfieldtemplateDto";
	}

	public static interface DynamicFieldTemplatePreAuthorizeEnum {
		public static final String AUTHORITY_READ = "dynamicfieldtemplate_read";
		public static final String AUTHORITY_CREATE = "dynamicfieldtemplate_create";
		public static final String AUTHORITY_UPDATE = "dynamicfieldtemplate_update";
		public static final String AUTHORITY_DELETE = "dynamicfieldtemplate_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

}
