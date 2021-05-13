package com.beanframework.workflow;

public class WorkflowConstants {

  public static interface Table {
    public static final String WORKFLOW = "workflow";
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.workflow.id.required";
    public static final String ID_EXISTS = "module.workflow.id.exists";
    public static final String UUID_NOT_EXISTS = "module.workflow.uuid.notexists";;
  }
}
