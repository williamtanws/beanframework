package com.beanframework.backoffice.api.data;

import java.util.ArrayList;
import java.util.List;

public class NotificationOverallDataTableResponseData {

  private int total;
  private String overallMessage;

  private List<NotificationMessageDataTableResponseData> notificationMessages =
      new ArrayList<NotificationMessageDataTableResponseData>();

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getOverallMessage() {
    return overallMessage;
  }

  public void setOverallMessage(String overallMessage) {
    this.overallMessage = overallMessage;
  }

  public List<NotificationMessageDataTableResponseData> getNotificationMessages() {
    return notificationMessages;
  }

  public void setNotificationMessages(
      List<NotificationMessageDataTableResponseData> notificationMessages) {
    this.notificationMessages = notificationMessages;
  }
}
