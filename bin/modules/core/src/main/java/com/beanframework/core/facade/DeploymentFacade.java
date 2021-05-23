package com.beanframework.core.facade;

import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.config.dto.DeploymentDto;

public interface DeploymentFacade {

  DeploymentDto findOneById(String id) throws BusinessException;

  Page<DeploymentDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();
}
