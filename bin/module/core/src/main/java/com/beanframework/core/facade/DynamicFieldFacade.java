package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldDto;

public interface DynamicFieldFacade {

	public static interface DynamicFieldPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "dynamicfield_read";
		public static final String AUTHORITY_CREATE = "dynamicfield_create";
		public static final String AUTHORITY_UPDATE = "dynamicfield_update";
		public static final String AUTHORITY_DELETE = "dynamicfield_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	DynamicFieldDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	DynamicFieldDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_CREATE)
	DynamicFieldDto create(DynamicFieldDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_UPDATE)
	DynamicFieldDto update(DynamicFieldDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	Page<DynamicFieldDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_CREATE)
	DynamicFieldDto createDto() throws Exception;

}
