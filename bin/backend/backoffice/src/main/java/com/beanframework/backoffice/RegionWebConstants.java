package com.beanframework.backoffice;

public interface RegionWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.region.checkid}";
			public static final String PAGE = "${path.api.region.page}";
			public static final String HISTORY = "${path.api.region.history}";
		}

		public static final String COMMENT = "${path.region}";
	}

	public interface View {
		public static final String LIST = "${view.region.list}";
	}

	public interface ModelAttribute {

		public static final String COMMENT_DTO = "regionDto";
	}
}
