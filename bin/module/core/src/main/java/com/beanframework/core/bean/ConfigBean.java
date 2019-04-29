package com.beanframework.core.bean;

public interface ConfigBean {

	String get(String id) throws Exception;
	
	boolean is(String id) throws Exception;

	String get(String id, String defaultValue) throws Exception;
	
	boolean is(String id, boolean defaultValue) throws Exception;

}
