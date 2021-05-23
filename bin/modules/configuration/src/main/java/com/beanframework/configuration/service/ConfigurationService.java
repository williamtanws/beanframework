package com.beanframework.configuration.service;

import com.beanframework.common.exception.InterceptorException;

public interface ConfigurationService {

  String get(String id) throws InterceptorException;

  String get(String id, String defaultValue) throws InterceptorException;
}
