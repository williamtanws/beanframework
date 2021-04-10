package com.beanframework.backoffice;

public interface MediaWebConstants {

	public interface Path {

		public interface Api {
			public static final String MEDIA_CHECKID = "${path.api.media.checkid}";
			public static final String MEDIA = "${path.api.media}";
			public static final String MEDIA_HISTORY = "${path.api.media.history}";
		}

		public static final String MEDIA = "${path.media}";
		public static final String MEDIA_FORM = "${path.media.form}";
		public static final String MEDIA_UPLOAD = "${path.media.upload}";
		public static final String MEDIA_BROWSE = "${path.media.browse}";
	}

	public interface View {
		public static final String MEDIA = "${view.media}";
		public static final String MEDIA_FORM = "${view.media.form}";
	}

	public interface ModelAttribute {
		public static final String MEDIA_DTO = "mediaDto";
	}
	
	public interface MediaPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "media_read";
		public static final String AUTHORITY_CREATE = "media_create";
		public static final String AUTHORITY_UPDATE = "media_update";
		public static final String AUTHORITY_DELETE = "media_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
