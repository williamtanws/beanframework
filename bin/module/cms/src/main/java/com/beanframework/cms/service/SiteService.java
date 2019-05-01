package com.beanframework.cms.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface SiteService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
