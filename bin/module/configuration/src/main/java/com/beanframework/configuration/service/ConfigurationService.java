package com.beanframework.configuration.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface ConfigurationService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
