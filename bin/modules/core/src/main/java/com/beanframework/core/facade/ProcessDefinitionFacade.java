package com.beanframework.core.facade;

import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.config.dto.ProcessDefinitionDto;

public interface ProcessDefinitionFacade {

  ProcessDefinitionDto findOneById(String deploymentId) throws BusinessException;

  Page<ProcessDefinitionDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();
}
