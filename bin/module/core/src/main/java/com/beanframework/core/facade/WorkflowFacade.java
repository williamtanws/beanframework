package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.WorkflowDto;

public interface WorkflowFacade {

	public static interface WorkflowPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "workflow_read";
		public static final String AUTHORITY_CREATE = "workflow_create";
		public static final String AUTHORITY_UPDATE = "workflow_update";
		public static final String AUTHORITY_DELETE = "workflow_delete";
		
		public static final String HAS_READ = "hasAuthority('"+AUTHORITY_READ+"')";
		public static final String HAS_CREATE = "hasAuthority('"+AUTHORITY_CREATE+"')";
		public static final String HAS_UPDATE = "hasAuthority('"+AUTHORITY_UPDATE+"')";
		public static final String HAS_DELETE = "hasAuthority('"+AUTHORITY_DELETE+"')";
	}

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_READ)
	WorkflowDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_READ)
	WorkflowDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_CREATE)
	WorkflowDto create(WorkflowDto model) throws BusinessException;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_UPDATE)
	WorkflowDto update(WorkflowDto model) throws BusinessException;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_READ)
	Page<WorkflowDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_CREATE)
	WorkflowDto createDto() throws Exception;
}
