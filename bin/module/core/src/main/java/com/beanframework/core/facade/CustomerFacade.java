package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CustomerDto;

public interface CustomerFacade {

	public static interface CustomerPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "customer_read";
		public static final String AUTHORITY_CREATE = "customer_create";
		public static final String AUTHORITY_UPDATE = "customer_update";
		public static final String AUTHORITY_DELETE = "customer_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	CustomerDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	CustomerDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_CREATE)
	CustomerDto create(CustomerDto model) throws BusinessException;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_UPDATE)
	CustomerDto update(CustomerDto model) throws BusinessException;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	Page<CustomerDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_CREATE)
	CustomerDto createDto() throws Exception;

	CustomerDto saveProfile(CustomerDto dto) throws BusinessException;

	CustomerDto getCurrentUser() throws Exception;

}
