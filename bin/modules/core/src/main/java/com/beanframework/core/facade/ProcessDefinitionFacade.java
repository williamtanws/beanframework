package com.beanframework.core.facade;

import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface ProcessDefinitionFacade {

  ProcessDefinition findOneById(String deploymentId) throws BusinessException;

  Page<ProcessDefinition> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;
}
