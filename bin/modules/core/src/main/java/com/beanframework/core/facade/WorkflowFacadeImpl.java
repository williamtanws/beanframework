package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private RepositoryService repositoryService;

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
		return save(model);
	}

	@Override
	public WorkflowDto update(WorkflowDto model) throws BusinessException {
		return save(model);
	}

	public WorkflowDto save(WorkflowDto workflow) throws BusinessException {
		if (workflow.getActive() == Boolean.TRUE && StringUtils.isBlank(workflow.getDeploymentId()) && StringUtils.isNotBlank(workflow.getClasspath())) {
			Deployment deployment = repositoryService.createDeployment().addClasspathResource(workflow.getClasspath()).deploy();
			workflow.setDeploymentId(deployment.getId());
		}

		if (workflow.getActive() == Boolean.FALSE && StringUtils.isBlank(workflow.getDeploymentId())) {
			repositoryService.deleteDeployment(workflow.getDeploymentId());
			workflow.setDeploymentId(null);
		}

		return save(workflow, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			Workflow workflow = modelService.findOneByUuid(uuid, entityClass);

			if (StringUtils.isBlank(workflow.getDeploymentId())) {
				repositoryService.deleteDeployment(workflow.getDeploymentId());
				workflow.setDeploymentId(null);
			}

			delete(uuid, entityClass);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
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
