package com.beanframework.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class UserConstants {

  private UserConstants() {
    throw new AssertionError();
  }

  public interface Query {
    public static final String SELECT_USER_BY_USER_GROUP =
        "SELECT u FROM User u LEFT JOIN u.userGroups g WHERE g = :uuid";
  }

  public interface Table {
    public static final String USER = "user";
    public static final String USER_ATTRIBUTE = "user_attribute";
    public static final String USER_AUTHORITY = "user_authority";
    public static final String USER_USER_GROUP_REL = "user_usergrouprel";
    public static final String USER_COMPANY_REL = "user_companyrel";
    public static final String USER_ADDRESS_REL = "user_addressrel";
    public static final String USER_PARAMETER = "user_param";
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.common.id.required";
    public static final String ID_EXISTS = "module.common.id.exists";
    public static final String UUID_NOT_EXISTS = "module.common.uuid.notexists";
    public static final String PASSWORD_REQUIRED = "module.common.password.required";
    public static final String SAVE_CURRENT_ADMIN_ERROR = "module.common.current.save.error";

    public static final String ACCOUNT_DISABLED = "module.common.account.disabled";
    public static final String ACCOUNT_EXPIRED = "module.common.account.expired";
    public static final String ACCOUNT_LOCKED = "module.common.account.locked";
    public static final String ACCOUNT_PASSWORD_EXPIRED = "module.common.account.password.expired";
    public static final String LOGIN_WRONG_USERNAME_PASSWORD =
        "module.common.login.error.wrongusernameorpassword";
  }

  public static final String MAX_SESSION_USER = "${module.user.session.max:-1}";
  public static final String MAX_SESSION_PREVENTS_LOGIN =
      "${module.user.session.prevents.login:false}";
  public static final String USER_MEDIA_LOCATION = "${module.user.media.location}";
  public static final String PATH_USER_PROFILE_PICTURE = "${path.user.profile.picture}";
  public static final String USER_PROFILE_PICTURE_THUMBNAIL_WIDTH =
      "${module.user.profile.picture.thumbnail.width}";
  public static final String USER_PROFILE_PICTURE_THUMBNAIL_HEIGHT =
      "${module.user.profile.picture.thumbnail.height}";

  public static interface Access {
    public static final String CONSOLE = "${module.console.access}";
    public static final String BACKOFFICE = "${module.backoffice.access}";
  }

  public static interface Admin {
    public static final String DEFAULT_ID = "${module.admin.default.id}";
    public static final String DEFAULT_PASSWORD = "${module.admin.default.password}";
    public static final String DEFAULT_GROUP = "${module.admin.default.group}";
  }

  public static interface Employee {
    public static final String DEFAULT_GROUP = "${module.employee.default.group}";
  }

  public static interface Customer {
    public static final String DEFAULT_GROUP = "${module.customer.default.group}";
  }

  public static interface Vendor {
    public static final String DEFAULT_GROUP = "${module.vendor.default.group}";
  }

  public static final DateFormat PARAMETER_DATE_FORMAT =
      new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
  public static final String LOGOUT_LAST_DATE = "logout.last.date";
  public static final String LOGIN_LAST_DATE = "login.last.date";
  public static final String LOGIN_ATTEMPT_COUNT = "login.attempt.count";
  public static final String LOGIN_ATTEMPT_MAX = "login.attempt.max";
  public static final String ACCOUNT_EXPIRED_DATE = "account.expired.date";
  public static final String PASSWORD_EXPIRED_DATE = "password.expired.date";
  public static final String PASSWORD_CHANGED_LAST_DATE = "password.changed.last.date";

  public static interface UserSettings {

    public static final String COOKIE_LOGIN_THEME = "login_theme";

    public static final String HEADER_THEME = "user.settings.header.theme";
    public static final String SIDEBAR_THEME = "user.settings.sidebar.theme";
    public static final String SIDEBAR_NAVFLATSTYLE = "user.settings.sidebar.navflatstyle";
    public static final String SIDEBAR_NAVLEGACYSTYLE = "user.settings.sidebar.navlegacystyle";
    public static final String SIDEBAR_NAVCOMPACT = "user.settings.sidebar.navcompact";
    public static final String SIDEBAR_NAVCHILDINDENT = "user.settings.sidebar.navchildindent";
    public static final String BODY_THEME = "user.settings.body.theme";
    public static final String BODY_SMALLTEXT = "user.settings.body.smalltext";

  }

}
