package com.beanframework.backoffice;

public interface DynamicFieldEnumWebConstants {

	public static final String LIST_SIZE = "${module.dynamicfieldenum.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.dynamicfieldenum.checkid}";
		}

		public static final String DYNAMICFIELDENUM = "${path.dynamicfieldenum}";
	}

	public interface View {
		public static final String LIST = "${view.dynamicfieldenum.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "dynamicfieldenumCreate";
		public static final String UPDATE = "dynamicfieldenumUpdate";
		public static final String SEARCH = "dynamicfieldenumSearch";
	}
}
