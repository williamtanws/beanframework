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
		public static final String READ = "hasAuthority('dynamicfieldslot_read')";
		public static final String CREATE = "hasAuthority('dynamicfieldslot_create')";
		public static final String UPDATE = "hasAuthority('dynamicfieldslot_update')";
		public static final String DELETE = "hasAuthority('dynamicfieldslot_delete')";
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.READ)
	DynamicFieldSlotDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.READ)
	DynamicFieldSlotDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.CREATE)
	DynamicFieldSlotDto create(DynamicFieldSlotDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.UPDATE)
	DynamicFieldSlotDto update(DynamicFieldSlotDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.READ)
	Page<DynamicFieldSlotDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.CREATE)
	DynamicFieldSlotDto createDto() throws Exception;

}
