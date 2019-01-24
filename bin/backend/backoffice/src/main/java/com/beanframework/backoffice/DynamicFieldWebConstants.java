package com.beanframework.backoffice;

public interface DynamicFieldWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.dynamicfield.checkid}";
			public static final String PAGE = "${path.api.dynamicfield.page}";
			public static final String HISTORY = "${path.api.dynamicfield.history}";
		}

		public static final String DYNAMICFIELD = "${path.dynamicfield}";
	}

	public interface View {
		public static final String LIST = "${view.dynamicfield.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "dynamicfieldCreate";
		public static final String UPDATE = "dynamicfieldUpdate";
	}
}
