package com.beanframework.backoffice;

public interface WorkflowWebConstants {

	public interface Path {

		public interface Api {
			public static final String WORKFLOW = "${path.api.workflow}";
			public static final String WORKFLOW_HISTORY = "${path.api.workflow.history}";
			public static final String WORKFLOW_CHECKID = "${path.api.workflow.checkid}";
		}

		public static final String WORKFLOW = "${path.workflow}";
		public static final String WORKFLOW_FORM = "${path.workflow.form}";
	}

	public interface View {
		public static final String PAGE = "${view.workflow}";
		public static final String FORM = "${view.workflow.form}";
	}

	public interface ModelAttribute {
		public static final String WORKFLOW_DTO = "workflowDto";
	}
	
	public interface WorkflowPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "workflow_read";
		public static final String AUTHORITY_CREATE = "workflow_create";
		public static final String AUTHORITY_UPDATE = "workflow_update";
		public static final String AUTHORITY_DELETE = "workflow_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
