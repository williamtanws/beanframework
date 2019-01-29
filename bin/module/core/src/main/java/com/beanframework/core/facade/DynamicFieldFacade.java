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
		public static final String READ = "hasAuthority('dynamicfield_read')";
		public static final String CREATE = "hasAuthority('dynamicfield_create')";
		public static final String UPDATE = "hasAuthority('dynamicfield_update')";
		public static final String DELETE = "hasAuthority('dynamicfield_delete')";
	}

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.READ)
	DynamicFieldDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.READ)
	DynamicFieldDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.CREATE)
	DynamicFieldDto create(DynamicFieldDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.UPDATE)
	DynamicFieldDto update(DynamicFieldDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<DynamicFieldDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	List<DynamicFieldDto> findAllDtoDynamicFields() throws Exception;

}
