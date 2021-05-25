package com.beanframework.user;

public interface CustomerConstants {

  public interface Discriminator {
    public static final String CUSTOMER = "Customer";
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.customer.id.required";
    public static final String ID_EXISTS = "module.customer.id.exists";
    public static final String UUID_NOT_EXISTS = "module.customer.uuid.notexists";
    public static final String ID_NOT_EXISTS = "module.customer.id.notexists";
    public static final String PASSWORD_REQUIRED = "module.customer.password.required";
    public static final String SAVE_CURRENT_CUSTOMER_ERROR = "module.customer.current.save.error";
  }

  public static final String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE =
      "customer.dynamicfield.template";

  public static final String CONFIGURATION_ACCOUNT_EXPIRY_HOURS_DEFAULT =
      "customer.account.expiry.hours.default";

  public static final String CONFIGURATION_PASSWORD_EXPIRY_HOURS_DEFAULT =
      "customer.password.expiry.hours.default";

  public static final String CONFIGURATION_ACCOUNT_LOGIN_ATTEMPT_MAX_DEFAULT =
      "customer.account.login.attempt.max.default";
}
