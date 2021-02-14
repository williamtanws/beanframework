package com.beanframework.backoffice;

public interface ImexWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.imex.checkid}";
			public static final String LIST = "${path.api.imex.page}";
			public static final String HISTORY = "${path.api.imex.history}";
		}

		public static final String IMEX = "${path.imex}";
	}

	public interface View {
		public static final String LIST = "${view.imex}";
	}

	public interface ModelAttribute {
		public static final String IMEX_DTO = "imexDto";
	}
	
	public interface ImexPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "imex_read";
		public static final String AUTHORITY_CREATE = "imex_create";
		public static final String AUTHORITY_UPDATE = "imex_update";
		public static final String AUTHORITY_DELETE = "imex_delete";
		
		public static final String HAS_READ = "hasAuthority('"+AUTHORITY_READ+"')";
		public static final String HAS_CREATE = "hasAuthority('"+AUTHORITY_CREATE+"')";
		public static final String HAS_UPDATE = "hasAuthority('"+AUTHORITY_UPDATE+"')";
		public static final String HAS_DELETE = "hasAuthority('"+AUTHORITY_DELETE+"')";
	}
}
