package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CountryDto;

public interface CountryFacade {

	public static interface CountryPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "country_read";
		public static final String AUTHORITY_CREATE = "country_create";
		public static final String AUTHORITY_UPDATE = "country_update";
		public static final String AUTHORITY_DELETE = "country_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	CountryDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	CountryDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_CREATE)
	CountryDto create(CountryDto model) throws BusinessException;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_UPDATE)
	CountryDto update(CountryDto model) throws BusinessException;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	Page<CountryDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	List<CountryDto> findAllDtoCountrys() throws Exception;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_CREATE)
	CountryDto createDto() throws Exception;

}
