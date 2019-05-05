package com.beanframework.console;

public interface ConsoleImportListenerConstants {

	public interface ConfigurationImport {
		public static final String KEY = "configuration";
		public static final String NAME = "Configuration";
		public static final int SORT = 10;
		public static final String DESCRIPTION = "Update/Remove Configuration Data";
	}

	public interface LanguageImport {
		public static final String KEY = "language";
		public static final String NAME = "Language";
		public static final int SORT = 20;
		public static final String DESCRIPTION = "Update/Remove Language Data";
	}

	public interface EnumerationImport {
		public static final String KEY = "enumeration";
		public static final String NAME = "Enumeration";
		public static final int SORT = 30;
		public static final String DESCRIPTION = "Update/Remove Enumeration Data";
	}

	public interface DynamicFieldImport {
		public static final String KEY = "dynamicfield";
		public static final String NAME = "Dynamic Field";
		public static final int SORT = 40;
		public static final String DESCRIPTION = "Update/Remove Dynamic Field Data";
	}

	public interface DynamicFieldSlotImport {
		public static final String KEY = "dynamicfieldslot";
		public static final String NAME = "Dynamic Field Slot";
		public static final int SORT = 41;
		public static final String DESCRIPTION = "Update/Remove Dynamic Field Slot Data";
	}

	public interface DynamicFieldTemplateImport {
		public static final String KEY = "dynamicfieldtemplate";
		public static final String NAME = "Dynamic Field Template";
		public static final int SORT = 42;
		public static final String DESCRIPTION = "Update/Remove Dynamic Field Template Data";
	}

	public interface UserRightImport {
		public static final String KEY = "userright";
		public static final String NAME = "User Right";
		public static final int SORT = 60;
		public static final String DESCRIPTION = "Update/Remove User Right Data";
	}

	public interface UserPermissionImport {
		public static final String KEY = "userpermission";
		public static final String NAME = "User Permission";
		public static final int SORT = 70;
		public static final String DESCRIPTION = "Update/Remove User Permission Data";
	}

	public interface UserGroupImport {
		public static final String KEY = "usergroup";
		public static final String NAME = "User Group";
		public static final int SORT = 80;
		public static final String DESCRIPTION = "Update/Remove User Group Data";
	}

	public interface UserAuthorityImport {
		public static final String KEY = "userauthority";
		public static final String NAME = "User Authority";
		public static final int SORT = 90;
		public static final String DESCRIPTION = "Update/Remove User Authority Data";
	}

	public interface MenuImport {
		public static final String KEY = "menu";
		public static final String NAME = "Menu";
		public static final int SORT = 100;
		public static final String DESCRIPTION = "Update/Remove Menu Data";
	}

	public interface CronjobImport {
		public static final String KEY = "cronjob";
		public static final String NAME = "Cronjob";
		public static final int SORT = 110;
		public static final String DESCRIPTION = "Update/Remove Cronjob Data. This would stop and resume all active cronjobs.";
	}

	public interface AdminImport {
		public static final String KEY = "admin";
		public static final String NAME = "Admin";
		public static final int SORT = 120;
		public static final String DESCRIPTION = "Update/Remove Admin Data";
	}

	public interface EmployeeImport {
		public static final String KEY = "employee";
		public static final String NAME = "Employee";
		public static final int SORT = 130;
		public static final String DESCRIPTION = "Update/Remove Employee Data";
	}

	public interface CustomerImport {
		public static final String KEY = "customer";
		public static final String NAME = "Customer";
		public static final int SORT = 131;
		public static final String DESCRIPTION = "Update/Remove Customer Data";
	}

	public interface VendorImport {
		public static final String KEY = "vendor";
		public static final String NAME = "Vendor";
		public static final int SORT = 132;
		public static final String DESCRIPTION = "Update/Remove Vendor Data";
	}

	public interface SiteImport {
		public static final String KEY = "site";
		public static final String NAME = "Site";
		public static final int SORT = 150;
		public static final String DESCRIPTION = "Update/Remove Site Data";
	}

	public interface MediaImport {
		public static final String KEY = "media";
		public static final String NAME = "Media";
		public static final int SORT = 160;
		public static final String DESCRIPTION = "Update/Remove Media Data";
	}

	public interface WorkflowImport {
		public static final String KEY = "workflow";
		public static final String NAME = "Workflow";
		public static final int SORT = 170;
		public static final String DESCRIPTION = "Update/Remove Workflow Data";
	}

}
