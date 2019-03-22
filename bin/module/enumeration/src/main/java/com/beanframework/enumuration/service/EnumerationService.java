package com.beanframework.enumuration.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.enumuration.domain.Enumeration;

public interface EnumerationService {

	Enumeration create() throws Exception;

	Enumeration findOneEntityByUuid(UUID uuid) throws Exception;

	Enumeration findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Enumeration> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	Enumeration saveEntity(Enumeration model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<Enumeration> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
