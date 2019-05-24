package com.beanframework.backoffice;

public interface ImexWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.imex.checkid}";
			public static final String PAGE = "${path.api.imex.page}";
			public static final String HISTORY = "${path.api.imex.history}";
		}

		public static final String IMEX = "${path.imex}";
	}

	public interface View {
		public static final String LIST = "${view.imex.list}";
	}

	public interface ModelAttribute {
		public static final String IMEX_DTO = "imexDto";
	}
}
