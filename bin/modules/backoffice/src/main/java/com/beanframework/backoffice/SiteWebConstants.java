package com.beanframework.backoffice;

public interface SiteWebConstants {

  public interface Path {

    public interface Api {
      public static final String SITE = "${path.api.site}";
      public static final String SITE_HISTORY = "${path.api.site.history}";
      public static final String SITE_CHECKID = "${path.api.site.checkid}";
    }

    public static final String SITE = "${path.site}";
    public static final String SITE_FORM = "${path.site.form}";
  }

  public interface View {
    public static final String SITE = "${view.site}";
    public static final String SITE_FORM = "${view.site.form}";
  }

  public interface ModelAttribute {
    public static final String SITE_DTO = "siteDto";
  }

  public interface SitePreAuthorizeEnum {
    public static final String AUTHORITY_READ = "site_read";
    public static final String AUTHORITY_CREATE = "site_create";
    public static final String AUTHORITY_UPDATE = "site_update";
    public static final String AUTHORITY_DELETE = "site_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
