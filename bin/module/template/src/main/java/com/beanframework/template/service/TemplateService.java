package com.beanframework.template.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface TemplateService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
