package com.beanframework.console;

public interface ExportWebConstants {

  public interface Path {
    public static final String EXPORT = "${path.export}";
  }

  public interface View {

    public static final String EXPORT = "${view.export}";
  }

  public static interface ExportPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "export_read";
    public static final String AUTHORITY_CREATE = "export_create";
    public static final String AUTHORITY_UPDATE = "export_update";
    public static final String AUTHORITY_DELETE = "export_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
