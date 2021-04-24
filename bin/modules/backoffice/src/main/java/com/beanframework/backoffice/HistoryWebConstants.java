package com.beanframework.backoffice;

public interface HistoryWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.history.checkid}";
		}

		public static final String HISTORY = "${path.history}";
	}

	public interface View {
		public static final String LIST = "${view.history}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "historyCreate";
		public static final String UPDATE = "historyUpdate";
	}

	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('history_read')";
	}
}
