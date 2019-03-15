package com.beanframework.backoffice;

public interface EnumerationWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.enumeration.checkid}";
			public static final String PAGE = "${path.api.enumeration.page}";
			public static final String HISTORY = "${path.api.enumeration.history}";
		}

		public static final String ENUMERATION = "${path.enumeration}";
	}

	public interface View {
		public static final String LIST = "${view.enumeration.list}";
	}

	public interface ModelAttribute {
		public static final String ENUMERATION_DTO = "enumerationDto";
	}
}
