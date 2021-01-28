package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.WorkflowDto;

public interface WorkflowFacade {

	WorkflowDto findOneByUuid(UUID uuid) throws Exception;

	WorkflowDto findOneProperties(Map<String, Object> properties) throws Exception;

	WorkflowDto create(WorkflowDto model) throws BusinessException;

	WorkflowDto update(WorkflowDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<WorkflowDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	WorkflowDto createDto() throws Exception;
}
