package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public interface DynamicFieldEnumService {

	DynamicFieldEnum create() throws Exception;

	DynamicFieldEnum findOneEntityByUuid(UUID uuid) throws Exception;

	DynamicFieldEnum findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<DynamicFieldEnum> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	<T> Page<DynamicFieldEnum> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	DynamicFieldEnum saveEntity(DynamicFieldEnum model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
}
