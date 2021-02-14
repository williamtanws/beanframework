package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.core.specification.WorkflowSpecification;
import com.beanframework.workflow.domain.Workflow;

@Component
public class WorkflowFacadeImpl extends AbstractFacade<Workflow, WorkflowDto> implements WorkflowFacade {

	private static final Class<Workflow> entityClass = Workflow.class;
	private static final Class<WorkflowDto> dtoClass = WorkflowDto.class;

	@Override
	public WorkflowDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public WorkflowDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public WorkflowDto create(WorkflowDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public WorkflowDto update(WorkflowDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<WorkflowDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, WorkflowSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass, dtoClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public WorkflowDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}
}
