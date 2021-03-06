package com.beanframework.user;

public final class UserGroupConstants {

  private UserGroupConstants() {
    throw new AssertionError();
  }

  public interface Table {
    public static final String USER_GROUP = "usergroup";
    public static final String USER_GROUP_ATTRIBUTE = "usergroup_attribute";
    public static final String USER_GROUP_USER_GROUP_REL = "usergroup_rel";

  }

  public interface Locale {
    public static final String ID_REQUIRED = "module.usergroup.id.required";
    public static final String ID_EXISTS = "module.usergroup.id.exists";
    public static final String UUID_NOT_EXISTS = "module.usergroup.uuid.notexists";;
  }

  public static final String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE =
      "usergroup.dynamicfield.template";
}
