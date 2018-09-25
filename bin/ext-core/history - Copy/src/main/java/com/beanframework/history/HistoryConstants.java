package com.beanframework.history;

public class HistoryConstants {

	public static interface Table {
		public static final String HISTORY = "history";
	}
	
	public static interface Locale {
		public static final String ID_REQUIRED = "module.history.id.required";
		public static final String ID_EXISTS = "module.history.id.exists";
		public static final String UUID_NOT_EXISTS = "module.history.uuid.notexists";;
	}
	
	public static interface Cache {
		public static final String HISTORY = "history";
		public static final String HISTORIES = "histories";
	}
}
