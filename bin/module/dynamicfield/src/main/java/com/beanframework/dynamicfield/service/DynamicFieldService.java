package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.dynamicfield.domain.DynamicField;

public interface DynamicFieldService {

	DynamicField create() throws Exception;

	DynamicField findOneEntityByUuid(UUID uuid) throws Exception;

	DynamicField findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<DynamicField> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	DynamicField saveEntity(DynamicField model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<DynamicField> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}