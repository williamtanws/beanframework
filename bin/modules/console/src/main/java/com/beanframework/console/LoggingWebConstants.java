package com.beanframework.console;

public class LoggingWebConstants {

  public interface Path {

    public interface Api {
      public static final String LOGGING_SETLEVEL = "${path.api.logging.setlevel}";
    }

    public static final String LOGGING = "${path.logging}";
  }

  public interface View {
    public static final String LOGGING = "${view.logging}";
  }

  public static interface LoggingPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "logging_read";
    public static final String AUTHORITY_CREATE = "logging_create";
    public static final String AUTHORITY_UPDATE = "logging_update";
    public static final String AUTHORITY_DELETE = "logging_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
