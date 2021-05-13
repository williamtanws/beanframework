package com.beanframework.backoffice;

public interface NotificationWebConstants {

  public interface Path {

    public interface Api {
      public static final String NOTIFICATION = "${path.api.notification}";
      public static final String NOTIFICATION_NEW = "${path.api.notification.new}";
      public static final String NOTIFICATION_CHECKED = "${path.api.notification.checked}";
    }

    public static final String NOTIFICATION = "${path.notification}";
  }

  public interface View {
    public static final String NOTIFICATION = "${view.notification}";
  }

  public interface ModelAttribute {
    public static final String NOTIFICATION_DTO = "notificationDto";
  }
}
