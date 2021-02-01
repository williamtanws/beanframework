package com.beanframework.backoffice;

public interface EnumerationWebConstants {

	public interface Path {

		public interface Api {
			public static final String ENUMERATION_PAGE = "${path.api.enumeration.page}";
			public static final String ENUMERATION_PAGE_HISTORY = "${path.api.enumeration.page.history}";
			public static final String ENUMERATION_CHECKID = "${path.api.enumeration.checkid}";
		}

		public static final String ENUMERATION_PAGE = "${path.enumeration.page}";
		public static final String ENUMERATION_FORM = "${path.enumeration.form}";
	}

	public interface View {
		public static final String PAGE = "${view.enumeration.page}";
		public static final String FORM = "${view.enumeration.form}";
	}

	public interface ModelAttribute {
		public static final String ENUMERATION_DTO = "enumerationDto";
	}
	
	public interface EnumerationPreAuthorizeEnum {
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
