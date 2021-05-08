package com.beanframework.imex;

public class ImexConstants {

	public static interface Table {
		public static final String IMEX = "imex";
		public static final String IMEX_MEDIA_REL = "imex_mediarel";
	}

	public static final String IMEX_MEDIA_FOLDER = "${module.imex.media.folder}";
	public static final String IMEX_IMPORT_INIT_LOCATIONS = "#{'${module.imex.import.init.locations}'.split(',')}";
	public static final String IMEX_IMPORT_UPDATE_LOCATIONS = "#{'${module.imex.import.update.locations}'.split(',')}";
}
