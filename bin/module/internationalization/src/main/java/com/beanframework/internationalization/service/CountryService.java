package com.beanframework.internationalization.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface CountryService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
