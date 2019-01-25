package com.beanframework.backoffice;

public interface SiteWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.site.checkid}";
			public static final String PAGE = "${path.api.site.page}";
			public static final String HISTORY = "${path.api.site.history}";
		}

		public static final String SITE = "${path.site}";
	}

	public interface View {
		public static final String LIST = "${view.site.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "siteCreate";
		public static final String UPDATE = "siteUpdate";
	}
}
