package com.beanframework.configuration.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.configuration.domain.Configuration;

public interface ConfigurationService {

	Configuration create() throws Exception;

	Configuration findOneEntityByUuid(UUID uuid) throws Exception;

	Configuration findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Configuration> findAllEntity() throws Exception;

	List<Configuration> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	<T> Page<Configuration> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	Configuration saveEntity(Configuration model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;
}
