package com.beanframework.backoffice;

public interface MyAccountWebConstants {

  public interface Path {

    public interface Api {
      public static final String MYACCOUNT = "${path.api.myaccount}";
      public static final String MYACCOUNT_CHECKID = "${path.api.myaccount.checkid}";
    }

    public static final String MYACCOUNT = "${path.myaccount}";
  }

  public interface View {
    public static final String MYACCOUNT_FORM = "${view.myaccount}";
  }

  public interface ModelAttribute {
    public static final String MYACCOUNT_DTO = "myaccountDto";
  }

  public interface MyAccountPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "myaccount_read";
    public static final String AUTHORITY_UPDATE = "myaccount_update";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
  }
}
