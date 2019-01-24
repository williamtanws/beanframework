package com.beanframework.customer.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.customer.domain.Customer;

public interface CustomerService {

	Customer create() throws Exception;

	Customer findOneEntityByUuid(UUID uuid) throws Exception;

	Customer findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Customer> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	Customer saveEntity(Customer model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<Customer> findEntityPage(DataTableRequest<T> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;
}
