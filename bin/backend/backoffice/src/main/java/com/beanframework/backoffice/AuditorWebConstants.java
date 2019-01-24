package com.beanframework.backoffice;

public interface AuditorWebConstants {

	public interface Path {

		public static final String LANGUAGE = "${path.auditor}";
		
		public interface Api {
			public static final String PAGE = "${path.api.auditor.page}";
			public static final String HISTORY = "${path.api.auditor.history}";
		}
	}

	public interface View {
		public static final String LIST = "${view.auditor.list}";
	}

	public interface ModelAttribute {

		public static final String UPDATE = "auditorUpdate";
	}
}
