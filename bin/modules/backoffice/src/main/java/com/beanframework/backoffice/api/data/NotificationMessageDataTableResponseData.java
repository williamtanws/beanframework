package com.beanframework.backoffice.api.data;

public class NotificationMessageDataTableResponseData {

	private String icon;
	private String message;
	private String timeAgo;
	private int total;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
