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
		public static final String READ = "hasAuthority('site_read')";
		public static final String CREATE = "hasAuthority('site_create')";
		public static final String UPDATE = "hasAuthority('site_update')";
		public static final String DELETE = "hasAuthority('site_delete')";
	}

	@PreAuthorize(SitePreAuthorizeEnum.READ)
	SiteDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.READ)
	SiteDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.CREATE)
	SiteDto create(SiteDto model) throws BusinessException;

	@PreAuthorize(SitePreAuthorizeEnum.UPDATE)
	SiteDto update(SiteDto model) throws BusinessException;

	@PreAuthorize(SitePreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(SitePreAuthorizeEnum.READ)
	Page<SiteDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(SitePreAuthorizeEnum.CREATE)
	SiteDto createDto() throws Exception;	
}
