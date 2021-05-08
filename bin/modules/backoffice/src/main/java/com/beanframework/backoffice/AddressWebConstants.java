package com.beanframework.backoffice;

public interface AddressWebConstants {

	public interface Path {

		public interface Api {
			public static final String ADDRESS = "${path.api.address}";
			public static final String ADDRESS_HISTORY = "${path.api.address.history}";
			public static final String ADDRESS_CHECKID = "${path.api.address.checkid}";
		}

		public static final String ADDRESS = "${path.address}";
		public static final String ADDRESS_FORM = "${path.address.form}";
	}

	public interface View {
		public static final String ADDRESS = "${view.address}";
		public static final String ADDRESS_FORM = "${view.address.form}";
	}

	public interface ModelAttribute {
		public static final String ADDRESS_DTO = "addressDto";
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
