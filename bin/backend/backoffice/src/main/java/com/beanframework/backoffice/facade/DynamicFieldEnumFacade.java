package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.DynamicFieldEnumDto;
import com.beanframework.backoffice.data.DynamicFieldEnumSearch;
import com.beanframework.common.exception.BusinessException;

public interface DynamicFieldEnumFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('dynamicfieldenum_read')";
		public static final String CREATE = "hasAuthority('dynamicfieldenum_create')";
		public static final String UPDATE = "hasAuthority('dynamicfieldenum_update')";
		public static final String DELETE = "hasAuthority('dynamicfieldenum_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<DynamicFieldEnumDto> findPage(DynamicFieldEnumSearch search, PageRequest pageRequest) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	DynamicFieldEnumDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	DynamicFieldEnumDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	DynamicFieldEnumDto create(DynamicFieldEnumDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	DynamicFieldEnumDto update(DynamicFieldEnumDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<DynamicFieldEnumDto> findAllDtoDynamicFieldEnums() throws Exception;

}
