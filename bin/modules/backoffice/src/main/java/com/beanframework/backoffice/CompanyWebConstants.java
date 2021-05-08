package com.beanframework.backoffice;

public interface CompanyWebConstants {

	public interface Path {

		public interface Api {
			public static final String COMPANY = "${path.api.company}";
			public static final String COMPANY_HISTORY = "${path.api.company.history}";
			public static final String COMPANY_CHECKID = "${path.api.company.checkid}";
		}

		public static final String COMPANY = "${path.company}";
		public static final String COMPANY_FORM = "${path.company.form}";
	}

	public interface View {
		public static final String COMPANY = "${view.company}";
		public static final String COMPANY_FORM = "${view.company.form}";
	}

	public interface ModelAttribute {
		public static final String COMPANY_DTO = "companyDto";
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
