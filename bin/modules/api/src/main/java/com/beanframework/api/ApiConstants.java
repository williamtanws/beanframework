package com.beanframework.api;

public class ApiConstants {

  public interface Path {
    public static final String API = "${path.api}";
  }

  public interface Role {
    public static final String ADMIN = "${module.api.role.admin}";
    public static final String USER = "${module.api.role.user}";
  }

  public static final String RESOURCE_ID = "";
}
