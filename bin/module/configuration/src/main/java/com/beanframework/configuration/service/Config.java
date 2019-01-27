package com.beanframework.configuration.service;

public interface Config {

	String get(String id) throws Exception;

	String get(String id, String defaultValue) throws Exception;

}
