package com.beanframework.backoffice;

public interface UserRightWebConstants {

  public interface Path {

    public interface Api {
      public static final String USERRIGHT = "${path.api.userright}";
      public static final String USERRIGHT_HISTORY = "${path.api.userright.history}";
      public static final String USERRIGHT_CHECKID = "${path.api.userright.checkid}";
    }

    public static final String USERRIGHT = "${path.userright}";
    public static final String USERRIGHT_FORM = "${path.userright.form}";
  }

  public interface View {
    public static final String USERRIGHT = "${view.userright}";
    public static final String USERRIGHT_FORM = "${view.userright.form}";
  }

  public interface ModelAttribute {
    public static final String USERRIGHT_DTO = "userrightDto";
  }

  public interface UserRightPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "userright_read";
    public static final String AUTHORITY_CREATE = "userright_create";
    public static final String AUTHORITY_UPDATE = "userright_update";
    public static final String AUTHORITY_DELETE = "userright_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
