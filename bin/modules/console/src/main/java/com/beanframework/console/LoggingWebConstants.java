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
}
