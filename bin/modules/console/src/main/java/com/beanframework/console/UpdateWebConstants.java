package com.beanframework.console;

public interface UpdateWebConstants {

  public interface Path {
    public static final String UPDATE = "${path.update}";

    public interface Api {

      public static final String UPDATE_TREE = "${path.api.update.tree}";
    }
  }

  public interface View {

    public static final String UPDATE = "${view.update}";
  }

  public static interface UpdatePreAuthorizeEnum {
    public static final String AUTHORITY_READ = "update_read";
    public static final String AUTHORITY_CREATE = "update_create";
    public static final String AUTHORITY_UPDATE = "update_update";
    public static final String AUTHORITY_DELETE = "update_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
