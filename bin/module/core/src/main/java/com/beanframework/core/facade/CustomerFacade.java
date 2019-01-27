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
		public static final String READ = "hasAuthority('customer_read')";
		public static final String CREATE = "hasAuthority('customer_create')";
		public static final String UPDATE = "hasAuthority('customer_update')";
		public static final String DELETE = "hasAuthority('customer_delete')";
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.READ)
	CustomerDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CustomerPreAuthorizeEnum.READ)
	CustomerDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CustomerPreAuthorizeEnum.CREATE)
	CustomerDto create(CustomerDto model) throws BusinessException;

	@PreAuthorize(CustomerPreAuthorizeEnum.UPDATE)
	CustomerDto update(CustomerDto model) throws BusinessException;

	@PreAuthorize(CustomerPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<CustomerDto> findPage(DataTableRequest<CustomerDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<CustomerDto> findAllDtoCustomers() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

}