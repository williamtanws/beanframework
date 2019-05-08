package com.beanframework.core.facade;

import java.util.Map;

import org.flowable.task.api.Task;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface TaskFacade {

	public static interface TaskPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "task_read";
		public static final String AUTHORITY_CREATE = "task_create";
		public static final String AUTHORITY_UPDATE = "task_update";
		public static final String AUTHORITY_DELETE = "task_delete";
		
		public static final String HAS_READ = "hasAuthority('"+AUTHORITY_READ+"')";
		public static final String HAS_CREATE = "hasAuthority('"+AUTHORITY_CREATE+"')";
		public static final String HAS_UPDATE = "hasAuthority('"+AUTHORITY_UPDATE+"')";
		public static final String HAS_DELETE = "hasAuthority('"+AUTHORITY_DELETE+"')";
	}

	@PreAuthorize(TaskPreAuthorizeEnum.HAS_READ)
	Task findOneById(String id);

	@PreAuthorize(TaskPreAuthorizeEnum.HAS_READ)
	Page<Task> findPage(DataTableRequest dataTableRequest);

	@PreAuthorize(TaskPreAuthorizeEnum.HAS_READ)
	int count();

	@PreAuthorize(TaskPreAuthorizeEnum.HAS_UPDATE)
	void complete(String taskId, Map<String, Object> variables) throws BusinessException;

	@PreAuthorize(TaskPreAuthorizeEnum.HAS_READ)
	Page<Task> findByCurrentUserGroup(DataTableRequest dataTableRequest) throws Exception;
}
