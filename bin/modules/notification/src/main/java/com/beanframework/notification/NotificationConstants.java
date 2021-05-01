package com.beanframework.notification;

import java.text.SimpleDateFormat;

public class NotificationConstants {

	public static interface Table {
		public static final String NOTIFICATION = "notification";
		public static final String NOTIFICATION_PARAMETER = "notification_param";
	}
	
	public static final String CACHE_NOTIFICATIONS = "notifications";

	public static final String USER_NOTIFICATION = "user_notification";
	public static final SimpleDateFormat USER_NOTIFICATION_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
