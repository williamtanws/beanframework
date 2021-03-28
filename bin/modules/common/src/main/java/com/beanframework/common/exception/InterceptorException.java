package com.beanframework.common.exception;

import com.beanframework.common.interceptor.Interceptor;

public class InterceptorException extends BusinessException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -85742332536700799L;
	private Interceptor interceptor;

	public InterceptorException(String message) {
		this(message, (Throwable) null, (Interceptor) null);
	}

	public InterceptorException(String message, Throwable cause) {
		this(message, cause, (Interceptor) null);
	}

	public InterceptorException(String message, Interceptor inter) {
		this(message, (Throwable) null, inter);
	}

	public InterceptorException(String message, Throwable cause, Interceptor inter) {
		super(message, cause);
		this.setInterceptor(inter);
	}

	public Interceptor getInterceptor() {
		return this.interceptor;
	}

	public void setInterceptor(Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	public String getMessage() {
		return super.getMessage();
	}
}