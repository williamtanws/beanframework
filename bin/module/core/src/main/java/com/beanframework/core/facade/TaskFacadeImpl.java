package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;

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

//	@Autowired
//	private UserService userService;

	@Override
	public Task findOneById(String id) {
		return taskService.createTaskQuery().taskId(id).singleResult();
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

	@Override
	public void complete(String taskId, Map<String, Object> variables) throws BusinessException {
		taskService.complete(taskId, variables);
	}

//	@Override
//	public Page<Task> findByCurrentUserGroup(DataTableRequest dataTableRequest) throws Exception {
//		User user = userService.getCurrentUser();
//
//		Set<String> userGroupIds = userService.getAllUserGroupIdsByUserUuid(user.getUuid());
//
//		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroupIn(new ArrayList<String>(userGroupIds)).listPage((int) dataTableRequest.getPageable().getOffset(),
//				dataTableRequest.getPageable().getPageSize());
//
//		return new PageImpl<Task>(tasks, PageRequest.of(dataTableRequest.getPageable().getPageNumber(), dataTableRequest.getPageable().getPageSize(), dataTableRequest.getPageable().getSort()),
//				tasks.size());
//	}

}
