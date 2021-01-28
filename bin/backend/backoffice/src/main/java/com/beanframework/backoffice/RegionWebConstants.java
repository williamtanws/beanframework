package com.beanframework.backoffice;

public interface RegionWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.region.checkid}";
			public static final String PAGE = "${path.api.region.page}";
			public static final String HISTORY = "${path.api.region.history}";
		}

		public static final String REGION = "${path.region}";
	}

	public interface View {
		public static final String LIST = "${view.region.list}";
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
