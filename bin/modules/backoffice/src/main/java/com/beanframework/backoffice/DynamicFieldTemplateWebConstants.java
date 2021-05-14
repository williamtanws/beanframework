package com.beanframework.backoffice;

public interface DynamicFieldTemplateWebConstants {

  public interface Path {

    public interface Api {
      public static final String DYNAMICFIELDTEMPLATE = "${path.api.dynamicfieldtemplate}";
      public static final String DYNAMICFIELDTEMPLATE_HISTORY =
          "${path.api.dynamicfieldtemplate.history}";
      public static final String DYNAMICFIELDTEMPLATE_CHECKID =
          "${path.api.dynamicfieldtemplate.checkid}";
    }

    public static final String DYNAMICFIELDTEMPLATE = "${path.dynamicfieldtemplate}";
    public static final String DYNAMICFIELDTEMPLATE_FORM = "${path.dynamicfieldtemplate.form}";
  }

  public interface View {
    public static final String DYNAMICFIELDTEMPLATE = "${view.dynamicfieldtemplate}";
    public static final String DYNAMICFIELDTEMPLATE_FORM = "${view.dynamicfieldtemplate.form}";
  }

  public interface ModelAttribute {
    public static final String DYNAMICFIELDTEMPLATE_DTO = "dynamicfieldtemplateDto";
  }

  public interface DynamicFieldTemplatePreAuthorizeEnum {
    public static final String AUTHORITY_READ = "dynamicfieldtemplate_read";
    public static final String AUTHORITY_CREATE = "dynamicfieldtemplate_create";
    public static final String AUTHORITY_UPDATE = "dynamicfieldtemplate_update";
    public static final String AUTHORITY_DELETE = "dynamicfieldtemplate_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
