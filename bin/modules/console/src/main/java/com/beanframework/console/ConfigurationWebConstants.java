package com.beanframework.console;

public interface ConfigurationWebConstants {

  public interface Path {

    public interface Api {
      public static final String CONFIGURATION = "${path.api.configuration}";
      public static final String CONFIGURATION_HISTORY = "${path.api.configuration.history}";
      public static final String CONFIGURATION_CHECKID = "${path.api.configuration.checkid}";
    }

    public static final String CONFIGURATION = "${path.configuration}";
    public static final String CONFIGURATION_FORM = "${path.configuration.form}";
  }

  public interface View {
    public static final String PAGE = "${view.configuration}";
    public static final String FORM = "${view.configuration.form}";
  }

  public interface ModelAttribute {
    public static final String CONFIGURATION_DTO = "configurationDto";
  }
}
