package com.beanframework.core.data;

import java.util.HashMap;
import java.util.Map;

import com.beanframework.common.data.GenericDto;

public class NotificationDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -78887161254267350L;
	private String type;
	private String message;
	Map<String, String> parameters = new HashMap<String, String>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
