package com.beanframework.core.facade;

import java.util.List;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

@Component
public class DeploymentFacadeImpl implements DeploymentFacade {

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public Deployment findOneById(String taskId) throws BusinessException {
		Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(taskId).singleResult();
		if (deployment == null) {
			throw new BusinessException("Could not find a deployment with id '" + taskId + "'.");
		}
		return deployment;
	}

	@Override
	public Page<Deployment> findPage(DataTableRequest dataTableRequest) throws Exception {
		List<Deployment> tasks = repositoryService.createDeploymentQuery().listPage((int) dataTableRequest.getPageable().getOffset(), dataTableRequest.getPageable().getPageSize());
		return new PageImpl<Deployment>(tasks, PageRequest.of(dataTableRequest.getPageable().getPageNumber(), dataTableRequest.getPageable().getPageSize(), dataTableRequest.getPageable().getSort()), tasks.size());
	}

	@Override
	public int count() throws Exception {
		return (int) repositoryService.createDeploymentQuery().count();
	}
}
