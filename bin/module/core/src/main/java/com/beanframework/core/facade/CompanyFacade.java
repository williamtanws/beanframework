package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CompanyDto;

public interface CompanyFacade {

	public static interface CompanyPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "company_read";
		public static final String AUTHORITY_CREATE = "company_create";
		public static final String AUTHORITY_UPDATE = "company_update";
		public static final String AUTHORITY_DELETE = "company_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	CompanyDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	CompanyDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_CREATE)
	CompanyDto create(CompanyDto model) throws BusinessException;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_UPDATE)
	CompanyDto update(CompanyDto model) throws BusinessException;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	Page<CompanyDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	List<CompanyDto> findAllDtoCompanys() throws Exception;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_CREATE)
	CompanyDto createDto() throws Exception;

}
