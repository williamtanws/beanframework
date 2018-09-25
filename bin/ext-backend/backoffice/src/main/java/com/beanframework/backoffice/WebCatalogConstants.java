package com.beanframework.backoffice;

public interface WebCatalogConstants {

	public static final String LIST_SIZE = "${module.catalog.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.catalog.checkid}";
		}

		public static final String CATALOG = "${path.catalog}";
	}

	public interface View {
		public static final String LIST = "${view.catalog.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "catalogCreate";
		public static final String UPDATE = "catalogUpdate";
		public static final String SEARCH = "catalogSearch";
	}
	
	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('catalog_read')";
		public static final String CREATE = "hasAuthority('catalog_create')";
		public static final String UPDATE = "hasAuthority('catalog_update')";
		public static final String DELETE = "hasAuthority('catalog_delete')";
	}
}
