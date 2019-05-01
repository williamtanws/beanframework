package com.beanframework.dynamicfield.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface DynamicFieldTemplateService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
