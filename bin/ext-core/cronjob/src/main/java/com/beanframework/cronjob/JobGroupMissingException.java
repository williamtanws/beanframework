package com.beanframework.cronjob;

public class JobGroupMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JobGroupMissingException(String msg) {
		super(msg);
	}

	public JobGroupMissingException(String msg, Throwable t) {
		super(msg, t);
	}
}