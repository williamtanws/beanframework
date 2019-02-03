package com.beanframework.backoffice;

public interface MediaWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.media.checkid}";
			public static final String PAGE = "${path.api.media.page}";
			public static final String HISTORY = "${path.api.media.history}";
		}

		public static final String MEDIA = "${path.media}";
	}

	public interface View {
		public static final String LIST = "${view.media.list}";
	}

	public interface ModelAttribute {
		public static final String MEDIA_DTO = "mediaDto";
	}
}
