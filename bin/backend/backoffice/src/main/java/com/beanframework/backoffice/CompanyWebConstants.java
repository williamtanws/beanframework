package com.beanframework.backoffice;

public interface CompanyWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.company.checkid}";
			public static final String PAGE = "${path.api.company.page}";
			public static final String HISTORY = "${path.api.company.history}";
		}

		public static final String COMMENT = "${path.company}";
	}

	public interface View {
		public static final String LIST = "${view.company.list}";
	}

	public interface ModelAttribute {

		public static final String COMMENT_DTO = "companyDto";
	}
}
