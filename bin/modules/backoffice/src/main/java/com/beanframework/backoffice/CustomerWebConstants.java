package com.beanframework.backoffice;

public interface CustomerWebConstants {

  public interface Path {

    public interface Api {
      public static final String CUSTOMER = "${path.api.customer}";
      public static final String CUSTOMER_HISTORY = "${path.api.customer.history}";
      public static final String CUSTOMER_CHECKID = "${path.api.customer.checkid}";
    }

    public static final String CUSTOMER = "${path.customer}";
    public static final String CUSTOMER_FORM = "${path.customer.form}";
  }

  public interface View {
    public static final String CUSTOMER = "${view.customer}";
    public static final String CUSTOMER_FORM = "${view.customer.form}";
  }

  public interface ModelAttribute {
    public static final String CUSTOMER_DTO = "customerDto";
  }

  public interface CustomerPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "customer_read";
    public static final String AUTHORITY_CREATE = "customer_create";
    public static final String AUTHORITY_UPDATE = "customer_update";
    public static final String AUTHORITY_DELETE = "customer_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
