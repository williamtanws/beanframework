package com.beanframework.backoffice;

public interface TaskWebConstants {

	public interface Path {

		public interface Api {
			public static final String PAGE = "${path.api.task.page}";
			public static final String HISTORY = "${path.api.task.history}";
		}

		public static final String TASK = "${path.task}";
	}

	public interface View {
		public static final String LIST = "${view.task.list}";
	}

	public interface ModelAttribute {
		public static final String TASK_DTO = "taskDto";
	}
}
