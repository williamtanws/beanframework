package com.beanframework.core.facade;

import java.util.List;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

@Component
public class TaskFacadeImpl implements TaskFacade {

	@Autowired
	private TaskService taskService;

	@Override
	public Task findOneById(String taskId) throws BusinessException {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		if (task == null) {
			throw new BusinessException("Could not find a task with id '" + taskId + "'.");
		}
		return task;
	}

	@Override
	public Page<Task> findPage(DataTableRequest dataTableRequest) {
		List<Task> tasks = taskService.createTaskQuery().listPage((int) dataTableRequest.getPageable().getOffset(), dataTableRequest.getPageable().getPageSize());

		return new PageImpl<Task>(tasks, PageRequest.of(dataTableRequest.getPageable().getPageNumber(), dataTableRequest.getPageable().getPageSize(), dataTableRequest.getPageable().getSort()),
				tasks.size());
	}

	@Override
	public int count() {
		return (int) taskService.createTaskQuery().count();
	}
}
