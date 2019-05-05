package com.beanframework.backoffice;

public interface WorkflowWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.workflow.checkid}";
			public static final String PAGE = "${path.api.workflow.page}";
			public static final String HISTORY = "${path.api.workflow.history}";
		}

		public static final String WORKFLOW = "${path.workflow}";
	}

	public interface View {
		public static final String LIST = "${view.workflow.list}";
	}

	public interface ModelAttribute {
		public static final String WORKFLOW_DTO = "workflowDto";
	}
}
