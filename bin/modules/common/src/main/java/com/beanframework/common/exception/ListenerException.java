package com.beanframework.common.exception;

import com.beanframework.common.interceptor.Interceptor;

public class ListenerException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5072009251775395690L;
	private Interceptor interceptor;

	public ListenerException(String message) {
		this(message, (Throwable) null, (Interceptor) null);
	}

	public ListenerException(String message, Throwable cause) {
		this(message, cause, (Interceptor) null);
	}

	public ListenerException(String message, Interceptor inter) {
		this(message, (Throwable) null, inter);
	}

	public ListenerException(String message, Throwable cause, Interceptor inter) {
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
		return "[" + this.interceptor + "]:" + super.getMessage();
	}
}