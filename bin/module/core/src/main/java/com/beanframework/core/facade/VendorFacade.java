package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.VendorDto;

public interface VendorFacade {

	public static interface VendorPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "vendor_read";
		public static final String AUTHORITY_CREATE = "vendor_create";
		public static final String AUTHORITY_UPDATE = "vendor_update";
		public static final String AUTHORITY_DELETE = "vendor_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_READ)
	VendorDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_READ)
	VendorDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_CREATE)
	VendorDto create(VendorDto model) throws BusinessException;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_UPDATE)
	VendorDto update(VendorDto model) throws BusinessException;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_READ)
	Page<VendorDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.HAS_CREATE)
	VendorDto createDto() throws Exception;

	VendorDto saveProfile(VendorDto dto) throws BusinessException;

	VendorDto getCurrentUser() throws Exception;

}
