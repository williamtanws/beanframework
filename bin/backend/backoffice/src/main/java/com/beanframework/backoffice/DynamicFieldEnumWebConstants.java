package com.beanframework.backoffice;

public interface DynamicFieldEnumWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.dynamicfieldenum.checkid}";
			public static final String PAGE = "${path.api.dynamicfieldenum.page}";
			public static final String HISTORY = "${path.api.dynamicfieldenum.history}";
		}

		public static final String DYNAMICFIELDENUM = "${path.dynamicfieldenum}";
	}

	public interface View {
		public static final String LIST = "${view.dynamicfieldenum.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "dynamicfieldenumCreate";
		public static final String UPDATE = "dynamicfieldenumUpdate";
	}
}
