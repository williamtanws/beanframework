package com.beanframework.configuration.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.configuration.domain.Configuration;

public interface ConfigurationService {

	Configuration findOneEntityByUuid(UUID uuid) throws Exception;

	Configuration findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Configuration> findAllEntity() throws Exception;

	List<Configuration> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	Configuration saveEntity(Configuration model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	Page<Configuration> findEntityPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
