package com.beanframework.console;

public interface OverviewWebConstants {

  public static final String PAGINATION = "pagination";

  public interface Pagination {
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String DIRECTION = "direction";
    public static final String PROPERTIES = "properties";
    public static final String PROPERTIES_SPLIT = ",";
  }

  public interface Param {
    public static final String UUID = "uuid";
    public static final String ID = "id";
  }

  public interface Model {
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";
    public static final String INFO = "info";
    public static final String PAGINATION = "pagination";
  }

  public interface Path {
    public static final String APPLICATION_OVERVIEW = "${path.console.application.overview}";
  }

  public interface View {
    public static final String APPLICATION_OVERVIEW = "${view.console.application.overview}";
  }

  public interface Locale {
    public static final String SAVE_SUCCESS = "module.common.save.success";
    public static final String SAVE_FAIL = "module.common.save.fail";
    public static final String DELETE_SUCCESS = "module.common.delete.success";
    public static final String DELETE_FAIL = "module.common.delete.fail";
    public static final String LOGIN_WRONG_USERNAME_PASSWORD =
        "module.common.login.error.wrongusernameorpassword";
    public static final String RECORD_UUID_NOT_FOUND = "module.common.record.uuid.notfound";
  }

  public static interface OverviewPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "overview_read";
    public static final String AUTHORITY_CREATE = "overview_create";
    public static final String AUTHORITY_UPDATE = "overview_update";
    public static final String AUTHORITY_DELETE = "overview_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
