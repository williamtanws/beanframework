package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.dynamicfield.domain.DynamicField;

public interface DynamicFieldService {

	DynamicField create() throws Exception;

	DynamicField findOneEntityByUuid(UUID uuid) throws Exception;

	DynamicField findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<DynamicField> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	<T> Page<DynamicField> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	DynamicField saveEntity(DynamicField model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
}
