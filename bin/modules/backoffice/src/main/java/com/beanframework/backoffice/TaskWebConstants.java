package com.beanframework.backoffice;

public interface TaskWebConstants {

	public interface Path {

		public interface Api {
			public static final String TASK = "${path.api.task}";
			public static final String TASK_HISTORY = "${path.api.task.history}";
			public static final String TASK_CHECKID = "${path.api.task.checkid}";
		}

		public static final String TASK = "${path.task}";
		public static final String TASK_FORM = "${path.task.form}";
	}

	public interface View {
		public static final String TASK = "${view.task}";
		public static final String TASK_FORM = "${view.task.form}";
	}

	public interface ModelAttribute {
		public static final String TASK = "task";
	}

	public interface TaskPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "task_read";
		public static final String AUTHORITY_CREATE = "task_create";
		public static final String AUTHORITY_UPDATE = "task_update";
		public static final String AUTHORITY_DELETE = "task_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
