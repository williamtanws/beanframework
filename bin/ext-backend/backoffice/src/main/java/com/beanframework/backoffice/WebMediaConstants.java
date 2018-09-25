package com.beanframework.backoffice;

public interface WebMediaConstants {

	public static final String LIST_SIZE = "${module.media.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.media.checkid}";
		}

		public static final String MEDIA = "${path.media}";
	}

	public interface View {
		public static final String LIST = "${view.media.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "mediaCreate";
		public static final String UPDATE = "mediaUpdate";
		public static final String SEARCH = "mediaSearch";
	}
	
	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('media_read')";
		public static final String CREATE = "hasAuthority('media_create')";
		public static final String UPDATE = "hasAuthority('media_update')";
		public static final String DELETE = "hasAuthority('media_delete')";
	}
}
