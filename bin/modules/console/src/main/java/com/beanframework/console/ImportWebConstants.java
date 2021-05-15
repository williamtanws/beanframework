package com.beanframework.console;

public interface ImportWebConstants {

  public interface Path {
    public static final String IMPORT = "${path.import}";
  }

  public interface View {

    public static final String IMPORT = "${view.import}";
  }

  public static interface ImportPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "import_read";
    public static final String AUTHORITY_CREATE = "import_create";
    public static final String AUTHORITY_UPDATE = "import_update";
    public static final String AUTHORITY_DELETE = "import_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
