package com.beanframework.backoffice;

public interface WebCronjobConstants {

	public static final String LIST_SIZE = "${module.cronjob.list.size}";

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.cronjob.checkid}";
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

		public static final String CREATE = "cronjobCreate";
		public static final String UPDATE = "cronjobUpdate";
		public static final String SEARCH = "cronjobSearch";
	}
	
	public static interface PreAuthorize {
		public static final String READ = "hasAuthority('cronjob_read')";
		public static final String CREATE = "hasAuthority('cronjob_create')";
		public static final String UPDATE = "hasAuthority('cronjob_update')";
		public static final String DELETE = "hasAuthority('cronjob_delete')";
	}
}
