package com.beanframework.backoffice;

public interface DeploymentWebConstants {

  public interface Path {

    public interface Api {
      public static final String DEPLOYMENT = "${path.api.deployment}";
      public static final String DEPLOYMENT_HISTORY = "${path.api.deployment.history}";
      public static final String DEPLOYMENT_CHECKID = "${path.api.deployment.checkid}";
    }

    public static final String DEPLOYMENT = "${path.deployment}";
    public static final String DEPLOYMENT_FORM = "${path.deployment.form}";
  }

  public interface View {
    public static final String DEPLOYMENT = "${view.deployment}";
    public static final String DEPLOYMENT_FORM = "${view.deployment.form}";
  }

  public interface ModelAttribute {
    public static final String DEPLOYMENT = "deployment";
  }

  public interface DeploymentPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "deployment_read";
    public static final String AUTHORITY_CREATE = "deployment_create";
    public static final String AUTHORITY_UPDATE = "deployment_update";
    public static final String AUTHORITY_DELETE = "deployment_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
