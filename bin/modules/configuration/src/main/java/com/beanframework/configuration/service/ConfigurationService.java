package com.beanframework.configuration.service;

public interface ConfigurationService {

  String get(String id) throws Exception;

  String get(String id, String defaultValue) throws Exception;
}
