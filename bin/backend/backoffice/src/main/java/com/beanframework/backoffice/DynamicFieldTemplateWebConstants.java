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
}
