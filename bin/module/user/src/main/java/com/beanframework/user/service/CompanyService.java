package com.beanframework.user.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface CompanyService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}