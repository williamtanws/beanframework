package com.beanframework.core.bean;

public interface LocaleMessageBean {

	String get(String code) throws Exception;

	String get(String code, Object[] args) throws Exception;

}
