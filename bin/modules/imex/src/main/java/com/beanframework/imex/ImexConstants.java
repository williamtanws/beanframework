package com.beanframework.imex;

public class ImexConstants {

	public static interface Table {
		public static final String IMEX = "imex";
		public static final String IMEX_MEDIA_REL = "imex_mediarel";
	}

	public static final String IMEX_MEDIA_LOCATION = "${module.imex.media.location}";
	public static final String IMEX_IMPORT_LOCATION = "${module.imex.import.location}";
	public static final String IMEX_IMPORT_LISTENER_TYPES = "#{'${module.imex.import.listener.types}'.split(',')}";
}
