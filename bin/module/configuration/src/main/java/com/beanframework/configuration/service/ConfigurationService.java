package com.beanframework.configuration.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.beanframework.common.data.DataTableRequest;

public interface ConfigurationService {
	
	public static final String CACHE_CONFIGURATION = "Configuration";

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	@Cacheable(value = CACHE_CONFIGURATION, key = "#id")
	String get(String id) throws Exception;
}
