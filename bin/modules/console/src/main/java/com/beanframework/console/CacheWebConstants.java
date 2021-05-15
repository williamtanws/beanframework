package com.beanframework.console;

public class CacheWebConstants {

  public interface Path {
    public static final String CACHE = "${path.cache}";
    public static final String CACHE_CLEARALL = "${path.cache.clearall}";
  }

  public interface View {
    public static final String CACHE = "${view.cache}";
  }

  public interface Locale {
    public static final String CACHE_CLEARALL_SUCCESS = "module.cache.clearall.success";
  }

  public static interface CachePreAuthorizeEnum {
    public static final String AUTHORITY_READ = "cache_read";
    public static final String AUTHORITY_CREATE = "cache_create";
    public static final String AUTHORITY_UPDATE = "cache_update";
    public static final String AUTHORITY_DELETE = "cache_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
