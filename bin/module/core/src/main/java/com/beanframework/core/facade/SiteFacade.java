package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.SiteDto;

public interface SiteFacade {

	public static interface SitePreAuthorizeEnum {
		public static final String AUTHORITY_READ = "site_read";
		public static final String AUTHORITY_CREATE = "site_create";
		public static final String AUTHORITY_UPDATE = "site_update";
		public static final String AUTHORITY_DELETE = "site_delete";
		
		public static final String HAS_READ = "hasAuthority('"+AUTHORITY_READ+"')";
		public static final String HAS_CREATE = "hasAuthority('"+AUTHORITY_CREATE+"')";
		public static final String HAS_UPDATE = "hasAuthority('"+AUTHORITY_UPDATE+"')";
		public static final String HAS_DELETE = "hasAuthority('"+AUTHORITY_DELETE+"')";
	}

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	SiteDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	SiteDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_CREATE)
	SiteDto create(SiteDto model) throws BusinessException;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_UPDATE)
	SiteDto update(SiteDto model) throws BusinessException;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	Page<SiteDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_CREATE)
	SiteDto createDto() throws Exception;
}
