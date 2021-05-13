package com.beanframework.email;

public class EmailConstants {

  public static interface Table {
    public static final String EMAIL = "email";
    public static final String EMAIL_MEDIA_REL = "email_media_rel";
  }

  public static interface Locale {
    public static final String UUID_NOT_EXISTS = "module.email.uuid.notexists";;
  }

  public static final String EMAIL_MEDIA_LOCATION = "${module.email.media.location}";
}
