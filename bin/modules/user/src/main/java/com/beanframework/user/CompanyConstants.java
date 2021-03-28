package com.beanframework.user;

public class CompanyConstants {

	public static interface Table {
		public static final String COMPANY = "company";
		public static final String COMPANY_ADDRESS_REL = "company_addressrel";
	}

	public static interface Locale {
		public static final String ID_REQUIRED = "module.company.id.required";
		public static final String ID_EXISTS = "module.company.id.exists";
		public static final String UUID_NOT_EXISTS = "module.company.uuid.notexists";;
	}
}
