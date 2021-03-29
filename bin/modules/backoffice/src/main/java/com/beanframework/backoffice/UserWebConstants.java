package com.beanframework.backoffice;

public interface UserWebConstants {

	public interface Path {

		public interface Api {
			public static final String PAGE = "${path.api.user}";
		}
	}
	
	public static interface UserPreAuthorizeEnum {

		public static final String AUTHORITY_READ = "user_read";
		public static final String AUTHORITY_CREATE = "user_create";
		public static final String AUTHORITY_UPDATE = "user_update";
		public static final String AUTHORITY_DELETE = "user_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
