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
		public static final String LIST = "${view.company}";
	}

	public interface ModelAttribute {

		public static final String COMMENT_DTO = "companyDto";
	}
	
	public interface CompanyPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "company_read";
		public static final String AUTHORITY_CREATE = "company_create";
		public static final String AUTHORITY_UPDATE = "company_update";
		public static final String AUTHORITY_DELETE = "company_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

}
