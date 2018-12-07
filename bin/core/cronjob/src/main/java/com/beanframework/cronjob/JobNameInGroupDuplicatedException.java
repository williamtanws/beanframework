package com.beanframework.cronjob;

public class JobNameInGroupDuplicatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JobNameInGroupDuplicatedException(String msg) {
		super(msg);
	}

	public JobNameInGroupDuplicatedException(String msg, Throwable t) {
		super(msg, t);
	}
}