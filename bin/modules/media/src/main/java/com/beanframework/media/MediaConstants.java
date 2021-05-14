package com.beanframework.media;

public class MediaConstants {

  public static interface Table {
    public static final String MEDIA = "media";
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.media.id.required";
    public static final String ID_EXISTS = "module.media.id.exists";
    public static final String UUID_NOT_EXISTS = "module.media.uuid.notexists";;
  }

  public static final String MEDIA_LOCATION = "${module.media.location}";
  public static final String MEDIA_URL = "${path.media.url}";
}
