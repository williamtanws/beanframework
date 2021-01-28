package com.beanframework.backoffice;

public interface CronjobWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.cronjob.checkid}";
			public static final String PAGE = "${path.api.cronjob.page}";
			public static final String HISTORY = "${path.api.cronjob.history}";
			public static final String CHECKJOBGROUPNAME = "${path.api.cronjob.checkjobgroupname}";
			public static final String CHECKJOBCLASS = "${path.api.cronjob.checkjobclass}";
			public static final String CHECKCRONEXPRESSION = "${path.api.cronjob.checkcronexpression}";
		}

		public static final String CRONJOB = "${path.cronjob}";
	}

	public interface View {
		public static final String LIST = "${view.cronjob.list}";
	}

	public interface ModelAttribute {
		public static final String CRONJOB_DTO = "cronjobDto";
	}
	

	public interface CronjobPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "cronjob_read";
		public static final String AUTHORITY_CREATE = "cronjob_create";
		public static final String AUTHORITY_UPDATE = "cronjob_update";
		public static final String AUTHORITY_DELETE = "cronjob_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
