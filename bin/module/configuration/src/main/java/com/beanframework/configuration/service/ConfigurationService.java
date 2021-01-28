package com.beanframework.configuration.service;

import org.springframework.cache.annotation.Cacheable;

public interface ConfigurationService {
	
	public static final String CACHE_CONFIGURATION = "Configuration";

	@Cacheable(value = CACHE_CONFIGURATION, key = "#id")
	String get(String id) throws Exception;
}
