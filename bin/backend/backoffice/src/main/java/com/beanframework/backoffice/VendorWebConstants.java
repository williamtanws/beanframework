package com.beanframework.backoffice;

public interface VendorWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.vendor.checkid}";
			public static final String PAGE = "${path.api.vendor.page}";
			public static final String HISTORY = "${path.api.vendor.history}";
		}

		public static final String VENDOR = "${path.vendor}";
		public static final String PROFILE = "${path.vendor.profile}";
	}

	public interface View {
		public static final String PROFILE = "${view.vendor.profile}";
		public static final String LIST = "${view.vendor.list}";
	}

	public interface ModelAttribute {
		public static final String VENDOR_DTO = "vendorDto";
		public static final String PROFILE = "vendorProfile";
	}
}
