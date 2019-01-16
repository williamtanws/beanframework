package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.CustomerDto;
import com.beanframework.backoffice.data.CustomerSearch;
import com.beanframework.common.exception.BusinessException;

public interface CustomerFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('customer_read')";
		public static final String CREATE = "hasAuthority('customer_create')";
		public static final String UPDATE = "hasAuthority('customer_update')";
		public static final String DELETE = "hasAuthority('customer_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<CustomerDto> findPage(CustomerSearch search, PageRequest pageRequest) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	CustomerDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	CustomerDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	CustomerDto create(CustomerDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	CustomerDto update(CustomerDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<CustomerDto> findAllDtoCustomers() throws Exception;
}
