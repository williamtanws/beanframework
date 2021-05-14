package com.beanframework.core.facade;

import org.flowable.engine.repository.Deployment;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface DeploymentFacade {

  Deployment findOneById(String id) throws BusinessException;

  Page<Deployment> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;
}
