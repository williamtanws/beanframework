package com.beanframework.backoffice;

public interface RegionWebConstants {

	public interface Path {

		public interface Api {
			public static final String REGION = "${path.api.region}";
			public static final String REGION_HISTORY = "${path.api.region.history}";
			public static final String REGION_CHECKID = "${path.api.region.checkid}";
		}

		public static final String REGION = "${path.region}";
		public static final String REGION_FORM = "${path.region.form}";
	}

	public interface View {
		public static final String REGION = "${view.region}";
		public static final String REGION_FORM = "${view.region.form}";
	}

	public interface ModelAttribute {
		public static final String REGION_DTO = "regionDto";
	}

	public interface RegionPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "region_read";
		public static final String AUTHORITY_CREATE = "region_create";
		public static final String AUTHORITY_UPDATE = "region_update";
		public static final String AUTHORITY_DELETE = "region_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
