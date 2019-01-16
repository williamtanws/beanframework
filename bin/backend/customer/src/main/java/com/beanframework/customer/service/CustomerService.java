package com.beanframework.customer.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.customer.domain.Customer;

public interface CustomerService {

	Customer create() throws Exception;

	Customer findOneEntityByUuid(UUID uuid) throws Exception;

	Customer findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Customer> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	<T> Page<Customer> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	Customer saveEntity(Customer model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
}
