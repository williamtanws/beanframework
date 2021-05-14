package com.beanframework.backoffice;

public interface LogentryWebConstants {

  public interface Path {

    public interface Api {
      public static final String LOGENTRY = "${path.api.logentry}";
      public static final String LOGENTRY_HISTORY = "${path.api.logentry.history}";
      public static final String LOGENTRY_CHECKID = "${path.api.logentry.checkid}";
    }

    public static final String LOGENTRY = "${path.logentry}";
    public static final String LOGENTRY_FORM = "${path.logentry.form}";
  }

  public interface View {
    public static final String LOGENTRY = "${view.logentry}";
    public static final String LOGENTRY_FORM = "${view.logentry.form}";
  }

  public interface ModelAttribute {
    public static final String LOGENTRY_DTO = "logentryDto";
  }

  public interface LogentryPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "logentry_read";
    public static final String AUTHORITY_CREATE = "logentry_create";
    public static final String AUTHORITY_UPDATE = "logentry_update";
    public static final String AUTHORITY_DELETE = "logentry_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
