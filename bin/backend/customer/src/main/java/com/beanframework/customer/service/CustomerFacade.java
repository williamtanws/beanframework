package com.beanframework.customer.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.customer.domain.Customer;

public interface CustomerFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('customer_read')";
		public static final String CREATE = "hasAuthority('customer_create')";
		public static final String UPDATE = "hasAuthority('customer_update')";
		public static final String DELETE = "hasAuthority('customer_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<Customer> findPage(Specification<Customer> findByCriteria, PageRequest of) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	Customer findOneDtoByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	Customer findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	Customer createDto(Customer customerCreate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	Customer updateDto(Customer customerUpdate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Customer getCurrentUser();

	Customer findDtoAuthenticate(String id, String password) throws Exception;

	Customer create() throws Exception;

	Customer saveEntity(Customer model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

}
