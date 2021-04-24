package com.beanframework.core.facade;

import org.flowable.task.api.Task;
import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface TaskFacade {

	Task findOneById(String taskId) throws BusinessException;

	Page<Task> findPage(DataTableRequest dataTableRequest);

	int count();
}
