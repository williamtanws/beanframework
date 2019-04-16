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
		public static final String AUTHORITY_READ = "enumeration_read";
		public static final String AUTHORITY_CREATE = "enumeration_create";
		public static final String AUTHORITY_UPDATE = "enumeration_update";
		public static final String AUTHORITY_DELETE = "enumeration_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	EnumerationDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	EnumerationDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_CREATE)
	EnumerationDto create(EnumerationDto model) throws BusinessException;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_UPDATE)
	EnumerationDto update(EnumerationDto model) throws BusinessException;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	Page<EnumerationDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_CREATE)
	EnumerationDto createDto() throws Exception;
}
