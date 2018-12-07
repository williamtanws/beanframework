package com.beanframework.cronjob;

public class JobNameMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JobNameMissingException(String msg) {
		super(msg);
	}

	public JobNameMissingException(String msg, Throwable t) {
		super(msg, t);
	}
}