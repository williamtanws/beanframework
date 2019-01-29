package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldEnumDto;

public interface DynamicFieldEnumFacade {

	public static interface DynamicFieldEnumPreAuthorizeEnum {
		public static final String READ = "hasAuthority('dynamicfieldenum_read')";
		public static final String CREATE = "hasAuthority('dynamicfieldenum_create')";
		public static final String UPDATE = "hasAuthority('dynamicfieldenum_update')";
		public static final String DELETE = "hasAuthority('dynamicfieldenum_delete')";
	}

	@PreAuthorize(DynamicFieldEnumPreAuthorizeEnum.READ)
	DynamicFieldEnumDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(DynamicFieldEnumPreAuthorizeEnum.READ)
	DynamicFieldEnumDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(DynamicFieldEnumPreAuthorizeEnum.CREATE)
	DynamicFieldEnumDto create(DynamicFieldEnumDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldEnumPreAuthorizeEnum.UPDATE)
	DynamicFieldEnumDto update(DynamicFieldEnumDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldEnumPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<DynamicFieldEnumDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	List<DynamicFieldEnumDto> findAllDtoDynamicFieldEnums() throws Exception;

}
