package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.RegionDto;

public interface RegionFacade {

	public static interface RegionPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "region_read";
		public static final String AUTHORITY_CREATE = "region_create";
		public static final String AUTHORITY_UPDATE = "region_update";
		public static final String AUTHORITY_DELETE = "region_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	RegionDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	RegionDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_CREATE)
	RegionDto create(RegionDto model) throws BusinessException;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_UPDATE)
	RegionDto update(RegionDto model) throws BusinessException;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	Page<RegionDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	List<RegionDto> findAllDtoRegions() throws Exception;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_CREATE)
	RegionDto createDto() throws Exception;

}
