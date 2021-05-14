package com.beanframework.core.facade;

import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.config.dto.TaskDto;

public interface TaskFacade {

  TaskDto findOneById(String id) throws BusinessException;

  Page<TaskDto> findPage(DataTableRequest dataTableRequest);

  int count();
}
