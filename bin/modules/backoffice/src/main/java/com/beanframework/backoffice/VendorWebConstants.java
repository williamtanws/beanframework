package com.beanframework.backoffice;

public interface VendorWebConstants {

	public interface Path {

		public interface Api {
			public static final String VENDOR = "${path.api.vendor}";
			public static final String VENDOR_HISTORY = "${path.api.vendor.history}";
			public static final String VENDOR_CHECKID = "${path.api.vendor.checkid}";
		}

		public static final String VENDOR = "${path.vendor}";
		public static final String VENDOR_FORM = "${path.vendor.form}";
	}

	public interface View {
		public static final String VENDOR = "${view.vendor}";
		public static final String VENDOR_FORM = "${view.vendor.form}";
	}

	public interface ModelAttribute {
		public static final String VENDOR_DTO = "vendorDto";
	}
	
	public interface VendorPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "vendor_read";
		public static final String AUTHORITY_CREATE = "vendor_create";
		public static final String AUTHORITY_UPDATE = "vendor_update";
		public static final String AUTHORITY_DELETE = "vendor_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
