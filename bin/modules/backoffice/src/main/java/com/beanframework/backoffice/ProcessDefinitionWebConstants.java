package com.beanframework.backoffice;

public interface ProcessDefinitionWebConstants {

  public interface Path {

    public interface Api {
      public static final String PROCESSDEFINITION = "${path.api.processdefinition}";
      public static final String PROCESSDEFINITION_HISTORY =
          "${path.api.processdefinition.history}";
      public static final String PROCESSDEFINITION_CHECKID =
          "${path.api.processdefinition.checkid}";
    }

    public static final String PROCESSDEFINITION = "${path.processdefinition}";
    public static final String PROCESSDEFINITION_FORM = "${path.processdefinition.form}";
  }

  public interface View {
    public static final String PROCESSDEFINITION = "${view.processdefinition}";
    public static final String PROCESSDEFINITION_FORM = "${view.processdefinition.form}";
  }

  public interface ModelAttribute {
    public static final String PROCESSDEFINITION = "processdefinition";
  }

  public interface ProcessDefinitionPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "processdefinition_read";
    public static final String AUTHORITY_CREATE = "processdefinition_create";
    public static final String AUTHORITY_UPDATE = "processdefinition_update";
    public static final String AUTHORITY_DELETE = "processdefinition_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
