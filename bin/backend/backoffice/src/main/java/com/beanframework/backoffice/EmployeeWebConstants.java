package com.beanframework.backoffice;

public interface EmployeeWebConstants {

	public interface Session {
		public interface Path {
			public static final String SESSION = "${path.employee.session}";
		}

		public interface View {
			public static final String SESSION_LIST = "${view.employee.session}";
		}
	}

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.employee.checkid}";
			public static final String PAGE = "${path.api.employee.page}";
			public static final String HISTORY = "${path.api.employee.history}";
		}

		public static final String EMPLOYEE = "${path.employee}";
		public static final String PROFILE = "${path.employee.profile}";
	}

	public interface View {
		public static final String PROFILE = "${view.employee.profile}";
		public static final String LIST = "${view.employee.list}";
	}

	public interface ModelAttribute {

		public static final String EMPLOYEE_DTO = "employeeDto";
		public static final String PROFILE = "employeeProfile";
	}

	public interface Locale {
		public static final String ACCOUNT_DISABLED = "module.employee.account.disabled";
		public static final String ACCOUNT_EXPIRED = "module.employee.account.expired";
		public static final String ACCOUNT_LOCKED = "module.employee.account.locked";
		public static final String ACCOUNT_PASSWORD_EXPIRED = "module.employee.account.password.expired";
	}
	
	public static interface EmployeePreAuthorizeEnum {

		public static final String AUTHORITY_READ = "employee_read";
		public static final String AUTHORITY_CREATE = "employee_create";
		public static final String AUTHORITY_UPDATE = "employee_update";
		public static final String AUTHORITY_DELETE = "employee_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	public static interface EmployeeSessionPreAuthorizeEnum {

		public static final String AUTHORITY_READ = "employeesession_read";
		public static final String AUTHORITY_DELETE = "employeesession_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
