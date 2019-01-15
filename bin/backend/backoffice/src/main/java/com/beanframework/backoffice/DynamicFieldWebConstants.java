package com.beanframework.backoffice;

public interface DynamicFieldWebConstants {

	public static final String LIST_SIZE = "${module.dynamicfield.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.dynamicfield.checkid}";
		}

		public static final String DYNAMICFIELD = "${path.dynamicfield}";
	}

	public interface View {
		public static final String LIST = "${view.dynamicfield.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "dynamicfieldCreate";
		public static final String UPDATE = "dynamicfieldUpdate";
		public static final String SEARCH = "dynamicfieldSearch";
	}
}
