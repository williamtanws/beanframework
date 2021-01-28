package com.beanframework.backoffice;

public interface AddressWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.address.checkid}";
			public static final String PAGE = "${path.api.address.page}";
			public static final String HISTORY = "${path.api.address.history}";
		}

		public static final String PATH = "${path.address}";
	}

	public interface View {
		public static final String LIST = "${view.address.list}";
	}

	public interface ModelAttribute {

		public static final String DTO = "addressDto";
	}

	public interface AddressPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "address_read";
		public static final String AUTHORITY_CREATE = "address_create";
		public static final String AUTHORITY_UPDATE = "address_update";
		public static final String AUTHORITY_DELETE = "address_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
