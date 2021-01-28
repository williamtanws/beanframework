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
		public static final String DYNAMICFIELD_DTO = "dynamicfieldDto";
	}
	
	public interface DynamicFieldPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "dynamicfield_read";
		public static final String AUTHORITY_CREATE = "dynamicfield_create";
		public static final String AUTHORITY_UPDATE = "dynamicfield_update";
		public static final String AUTHORITY_DELETE = "dynamicfield_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
