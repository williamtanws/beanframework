package com.beanframework.console;

public interface SearchWebConstants {

  public interface Path {
    public static final String SEARCH = "${path.search}";
  }

  public interface View {

    public static final String SEARCH = "${view.search}";
  }

  public static interface SearchPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "search_read";
    public static final String AUTHORITY_CREATE = "search_create";
    public static final String AUTHORITY_UPDATE = "search_update";
    public static final String AUTHORITY_DELETE = "search_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
