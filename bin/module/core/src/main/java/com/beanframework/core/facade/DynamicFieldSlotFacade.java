package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldSlotDto;

public interface DynamicFieldSlotFacade {

	public static interface DynamicFieldSlotPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "dynamicfieldslot_read";
		public static final String AUTHORITY_CREATE = "dynamicfieldslot_create";
		public static final String AUTHORITY_UPDATE = "dynamicfieldslot_update";
		public static final String AUTHORITY_DELETE = "dynamicfieldslot_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	DynamicFieldSlotDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	DynamicFieldSlotDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_CREATE)
	DynamicFieldSlotDto create(DynamicFieldSlotDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_UPDATE)
	DynamicFieldSlotDto update(DynamicFieldSlotDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	Page<DynamicFieldSlotDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_CREATE)
	DynamicFieldSlotDto createDto() throws Exception;

}
