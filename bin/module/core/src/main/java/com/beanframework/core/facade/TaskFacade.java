package com.beanframework.core.facade;

import java.util.Map;

import org.flowable.task.api.Task;
import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface TaskFacade {

	Task findOneById(String id);

	Page<Task> findPage(DataTableRequest dataTableRequest);

	int count();

	void complete(String taskId, Map<String, Object> variables) throws BusinessException;

	Page<Task> findByCurrentUserGroup(DataTableRequest dataTableRequest) throws Exception;
}
