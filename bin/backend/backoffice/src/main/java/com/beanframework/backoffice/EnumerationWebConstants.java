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
	
	public interface EnumPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "enumeration_read";
		public static final String AUTHORITY_CREATE = "enumeration_create";
		public static final String AUTHORITY_UPDATE = "enumeration_update";
		public static final String AUTHORITY_DELETE = "enumeration_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
