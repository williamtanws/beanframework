package com.beanframework.console;

public interface WebPlatformConstants {

	public interface Path {
		public static final String UPDATE = "${path.console.platform.update}";
		public static final String REMOVE = "${path.console.platform.remove}";
	}

	public interface View {

		public static final String UPDATE = "${view.console.platform.update}";
		public static final String REMOVE = "${view.console.platform.remove}";
	}

	public interface Initialize {

		public interface Language {
			public static final String KEY = "Language";
			public static final String NAME = "Language";
			public static final int SORT = 1;
			public static final String DESCRIPTION = "Delete all existing Language Data";
		}

		public interface UserRight {
			public static final String KEY = "UserRight";
			public static final String NAME = "User Right";
			public static final int SORT = 2;
			public static final String DESCRIPTION = "Delete all existing User Right Data";
		}

		public interface UserPermission {
			public static final String KEY = "UserPermission";
			public static final String NAME = "User Permission";
			public static final int SORT = 3;
			public static final String DESCRIPTION = "Delete all existing User Permission Data";
		}

		public interface UserGroup {
			public static final String KEY = "UserGroup";
			public static final String NAME = "User Group";
			public static final int SORT = 4;
			public static final String DESCRIPTION = "Delete all existing User Group Data";
		}

		public interface Menu {
			public static final String KEY = "Menu";
			public static final String NAME = "Menu";
			public static final int SORT = 5;
			public static final String DESCRIPTION = "Delete all existing Menu Data";
		}

		public interface Employee {
			public static final String KEY = "Employee";
			public static final String NAME = "Employee";
			public static final int SORT = 6;
			public static final String DESCRIPTION = "Delete all existing Employee Data";
		}

		public interface Customer {
			public static final String KEY = "Customer";
			public static final String NAME = "Customer";
			public static final int SORT = 7;
			public static final String DESCRIPTION = "Delete all existing Customer Data";
		}
		
		public interface Cronjob {
			public static final String KEY = "Cronjob";
			public static final String NAME = "Cronjob";
			public static final int SORT = 8;
			public static final String DESCRIPTION = "Delete all existing Cronjob Data";
		}

	}

	public interface Update {
		
		public interface Language {
			public static final String KEY = "Language";
			public static final String NAME = "Language";
			public static final int SORT = 10;
			public static final String DESCRIPTION = "Update Language Data";
		}

		public interface UserRight {
			public static final String KEY = "UserRight";
			public static final String NAME = "User Right";
			public static final int SORT = 20;
			public static final String DESCRIPTION = "Update User Right Data";
		}

		public interface UserPermission {
			public static final String KEY = "UserPermission";
			public static final String NAME = "User Permission";
			public static final int SORT = 30;
			public static final String DESCRIPTION = "Update User Permission Data";
		}

		public interface UserGroup {
			public static final String KEY = "UserGroup";
			public static final String NAME = "User Group";
			public static final int SORT = 40;
			public static final String DESCRIPTION = "Update User Group Data";
		}

		public interface UserAuthorityRight {
			public static final String KEY = "UserAuthority";
			public static final String NAME = "User Authority";
			public static final int SORT = 50;
			public static final String DESCRIPTION = "Update User Authority Data";
		}

		public interface Menu {
			public static final String KEY = "Menu";
			public static final String NAME = "Menu";
			public static final int SORT = 60;
			public static final String DESCRIPTION = "Update Menu Data";
		}

		public interface Employee {
			public static final String KEY = "Employee";
			public static final String NAME = "Employee";
			public static final int SORT = 70;
			public static final String DESCRIPTION = "Update Employee Data";
		}

		public interface Customer {
			public static final String KEY = "Customer";
			public static final String NAME = "Customer";
			public static final int SORT = 80;
			public static final String DESCRIPTION = "Update Customer Data";
		}
		
		public interface Cronjob {
			public static final String KEY = "Cronjob";
			public static final String NAME = "Cronjob";
			public static final int SORT = 90;
			public static final String DESCRIPTION = "Update Cronjob Data";
		}

	}

	public interface Remove {

		public interface Language {
			public static final String KEY = "Language";
			public static final String NAME = "Language";
			public static final int SORT = 1;
			public static final String DESCRIPTION = "Remove Language Data";
		}

		public interface UserRight {
			public static final String KEY = "UserRight";
			public static final String NAME = "User Right";
			public static final int SORT = 2;
			public static final String DESCRIPTION = "Remove User Right Data";
		}

		public interface UserPermission {
			public static final String KEY = "UserPermission";
			public static final String NAME = "User Permission";
			public static final int SORT = 3;
			public static final String DESCRIPTION = "Remove User Permission Data";
		}

		public interface UserGroup {
			public static final String KEY = "UserGroup";
			public static final String NAME = "User Group";
			public static final int SORT = 4;
			public static final String DESCRIPTION = "Remove User Group Data";
		}

		public interface UserAuthorityRight {
			public static final String KEY = "UserAuthority";
			public static final String NAME = "User Authority";
			public static final int SORT = 5;
			public static final String DESCRIPTION = "Remove User Authority Data";
		}

		public interface Menu {
			public static final String KEY = "Menu";
			public static final String NAME = "Menu";
			public static final int SORT = 6;
			public static final String DESCRIPTION = "Remove Menu Data";
		}

		public interface Employee {
			public static final String KEY = "Employee";
			public static final String NAME = "Employee";
			public static final int SORT = 7;
			public static final String DESCRIPTION = "Remove Employee Data";
		}

		public interface Customer {
			public static final String KEY = "Customer";
			public static final String NAME = "Customer";
			public static final int SORT = 8;
			public static final String DESCRIPTION = "Remove Customer Data";
		}
		
		public interface Cronjob {
			public static final String KEY = "Cronjob";
			public static final String NAME = "Cronjob";
			public static final int SORT = 9;
			public static final String DESCRIPTION = "Remove Cronjob Data";
		}
	}
}
