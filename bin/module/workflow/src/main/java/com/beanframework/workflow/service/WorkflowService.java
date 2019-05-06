package com.beanframework.workflow.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface WorkflowService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
