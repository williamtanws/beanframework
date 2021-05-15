package com.beanframework.console;

public class EnvironmentWebConstants {

  public interface Path {
    public static final String ENVIRONMENT = "${path.environment}";
  }

  public interface View {
    public static final String ENVIRONMENT = "${view.environment}";
  }

  public static interface EnvironmentPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "environment_read";
    public static final String AUTHORITY_CREATE = "environment_create";
    public static final String AUTHORITY_UPDATE = "environment_update";
    public static final String AUTHORITY_DELETE = "environment_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
