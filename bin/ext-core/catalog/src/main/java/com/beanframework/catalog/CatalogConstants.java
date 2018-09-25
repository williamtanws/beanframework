package com.beanframework.catalog;

public class CatalogConstants {

	public static interface Table {
		public static final String CATALOG = "catalog";
	}
	
	public static interface Locale {
		public static final String ID_REQUIRED = "module.catalog.id.required";
		public static final String ID_EXISTS = "module.catalog.id.exists";
		public static final String UUID_NOT_EXISTS = "module.catalog.uuid.notexists";;
	}
	
	public static interface Cache {
		public static final String CATALOG = "catalog";
		public static final String CATALOGS = "catalogs";
	}
}
