package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EnumerationDto;

public interface EnumerationFacade {

	public static interface EnumPreAuthorizeEnum {
		public static final String READ = "hasAuthority('enumeration_read')";
		public static final String CREATE = "hasAuthority('enumeration_create')";
		public static final String UPDATE = "hasAuthority('enumeration_update')";
		public static final String DELETE = "hasAuthority('enumeration_delete')";
	}

	@PreAuthorize(EnumPreAuthorizeEnum.READ)
	EnumerationDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(EnumPreAuthorizeEnum.READ)
	EnumerationDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(EnumPreAuthorizeEnum.CREATE)
	EnumerationDto create(EnumerationDto model) throws BusinessException;

	@PreAuthorize(EnumPreAuthorizeEnum.UPDATE)
	EnumerationDto update(EnumerationDto model) throws BusinessException;

	@PreAuthorize(EnumPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<EnumerationDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	List<EnumerationDto> findAllDtoEnums() throws Exception;

}
