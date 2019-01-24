package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.DynamicFieldDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface DynamicFieldFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('dynamicfield_read')";
		public static final String CREATE = "hasAuthority('dynamicfield_create')";
		public static final String UPDATE = "hasAuthority('dynamicfield_update')";
		public static final String DELETE = "hasAuthority('dynamicfield_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	DynamicFieldDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	DynamicFieldDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	DynamicFieldDto create(DynamicFieldDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	DynamicFieldDto update(DynamicFieldDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<DynamicFieldDto> findPage(DataTableRequest<DynamicFieldDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	List<DynamicFieldDto> findAllDtoDynamicFields() throws Exception;

}
