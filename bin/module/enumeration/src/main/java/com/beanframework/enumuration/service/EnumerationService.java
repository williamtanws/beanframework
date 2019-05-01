package com.beanframework.enumuration.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface EnumerationService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
