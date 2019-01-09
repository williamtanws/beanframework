package com.beanframework.backoffice;

public interface AuditorWebConstants {

	public static final String LIST_SIZE = "${module.auditor.list.size}";

	public interface Path {

		public static final String LANGUAGE = "${path.auditor}";
	}

	public interface View {
		public static final String LIST = "${view.auditor.list}";
	}

	public interface ModelAttribute {

		public static final String UPDATE = "auditorUpdate";
		public static final String SEARCH = "auditorSearch";
	}
}
