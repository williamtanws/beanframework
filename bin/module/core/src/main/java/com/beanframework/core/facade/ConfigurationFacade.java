package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.ConfigurationDto;

public interface ConfigurationFacade {

	ConfigurationDto findOneByUuid(UUID uuid) throws Exception;

	ConfigurationDto findOneProperties(Map<String, Object> properties) throws Exception;

	ConfigurationDto create(ConfigurationDto model) throws BusinessException;

	ConfigurationDto update(ConfigurationDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<ConfigurationDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	ConfigurationDto createDto() throws Exception;

	String get(String id, String defaultValue) throws Exception;

	boolean is(String id, boolean defaultValue) throws Exception;
}
