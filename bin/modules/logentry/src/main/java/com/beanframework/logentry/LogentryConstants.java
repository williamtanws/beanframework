package com.beanframework.logentry;

public class LogentryConstants {

  public static interface Table {
    public static final String LOGENTRY = "logentry";
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.logentry.id.required";
    public static final String ID_EXISTS = "module.logentry.id.exists";
    public static final String UUID_NOT_EXISTS = "module.logentry.uuid.notexists";;
  }
}
