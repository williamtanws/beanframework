package com.beanframework.backoffice;

public interface UserSessionWebConstants {

  public interface Path {

    public interface Api {
      public static final String USERSESSION_COUNT = "${path.api.user.session.count}";
    }

    public static final String USERSESSION = "${path.user.session}";
  }

  public interface View {
    public static final String USERSESSION_LIST = "${view.user.session}";
  }

  public static interface UserSessionPreAuthorizeEnum {

    public static final String AUTHORITY_READ = "usersession_read";
    public static final String AUTHORITY_CREATE = "usersession_create";
    public static final String AUTHORITY_UPDATE = "usersession_update";
    public static final String AUTHORITY_DELETE = "usersession_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
