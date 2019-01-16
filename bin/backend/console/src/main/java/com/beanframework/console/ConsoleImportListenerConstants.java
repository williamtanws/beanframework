package com.beanframework.console;

public interface ConsoleImportListenerConstants {

	public interface ConfigurationImportListener {
		public static final String KEY = "configuration";
		public static final String NAME = "Configuration";
		public static final int SORT = 5;
		public static final String DESCRIPTION = "Update/Remove Configuration Data";
	}
	
	public interface AdminImportListener {
		public static final String KEY = "admin";
		public static final String NAME = "Admin";
		public static final int SORT = 7;
		public static final String DESCRIPTION = "Update/Remove Admin Data";
	}

	public interface LanguageImportListener {
		public static final String KEY = "language";
		public static final String NAME = "Language";
		public static final int SORT = 10;
		public static final String DESCRIPTION = "Update/Remove Language Data";
	}

	public interface DynamicFieldImportListener {
		public static final String KEY = "dynamicfield";
		public static final String NAME = "Dynamic Field";
		public static final int SORT = 20;
		public static final String DESCRIPTION = "Update/Remove Dynamic Field Data";
	}

	public interface UserRightImportListener {
		public static final String KEY = "userright";
		public static final String NAME = "User Right";
		public static final int SORT = 30;
		public static final String DESCRIPTION = "Update/Remove User Right Data";
	}

	public interface UserPermissionImportListener {
		public static final String KEY = "userpermission";
		public static final String NAME = "User Permission";
		public static final int SORT = 40;
		public static final String DESCRIPTION = "Update/Remove User Permission Data";
	}

	public interface UserGroupImportListener {
		public static final String KEY = "usergroup";
		public static final String NAME = "User Group";
		public static final int SORT = 50;
		public static final String DESCRIPTION = "Update/Remove User Group Data";
	}

	public interface UserAuthorityImportListener {
		public static final String KEY = "userauthority";
		public static final String NAME = "User Authority";
		public static final int SORT = 60;
		public static final String DESCRIPTION = "Update/Remove User Authority Data";
	}

	public interface MenuImportListener {
		public static final String KEY = "menu";
		public static final String NAME = "Menu";
		public static final int SORT = 70;
		public static final String DESCRIPTION = "Update/Remove Menu Data";
	}

	public interface EmployeeImportListener {
		public static final String KEY = "employee";
		public static final String NAME = "Employee";
		public static final int SORT = 80;
		public static final String DESCRIPTION = "Update/Remove Employee Data";
	}

	public interface CustomerImportListener {
		public static final String KEY = "customer";
		public static final String NAME = "Customer";
		public static final int SORT = 90;
		public static final String DESCRIPTION = "Update/Remove Customer Data";
	}

	public interface CronjobImportListener {
		public static final String KEY = "cronjob";
		public static final String NAME = "Cronjob";
		public static final int SORT = 100;
		public static final String DESCRIPTION = "Update/Remove Cronjob Data. This would stop and resume all active cronjobs.";
	}
}
