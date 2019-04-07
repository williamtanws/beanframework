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
		public static final String READ = "hasAuthority('vendor_read')";
		public static final String CREATE = "hasAuthority('vendor_create')";
		public static final String UPDATE = "hasAuthority('vendor_update')";
		public static final String DELETE = "hasAuthority('vendor_delete')";
	}

	@PreAuthorize(VendorPreAuthorizeEnum.READ)
	VendorDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.READ)
	VendorDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.CREATE)
	VendorDto create(VendorDto model) throws BusinessException;

	@PreAuthorize(VendorPreAuthorizeEnum.UPDATE)
	VendorDto update(VendorDto model) throws BusinessException;

	@PreAuthorize(VendorPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(VendorPreAuthorizeEnum.READ)
	Page<VendorDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(VendorPreAuthorizeEnum.CREATE)
	VendorDto createDto() throws Exception;

	VendorDto saveProfile(VendorDto dto) throws BusinessException;

	VendorDto getCurrentUser() throws Exception;

}
