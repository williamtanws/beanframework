package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.AddressDto;

public interface AddressFacade {

	public static interface AddressPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "address_read";
		public static final String AUTHORITY_CREATE = "address_create";
		public static final String AUTHORITY_UPDATE = "address_update";
		public static final String AUTHORITY_DELETE = "address_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	AddressDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	AddressDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_CREATE)
	AddressDto create(AddressDto model) throws BusinessException;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_UPDATE)
	AddressDto update(AddressDto model) throws BusinessException;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	Page<AddressDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	List<AddressDto> findAllDtoAddresss() throws Exception;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_CREATE)
	AddressDto createDto() throws Exception;

}
