package com.beanframework.common.exception;

public class SystemException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -539482447609545019L;

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}