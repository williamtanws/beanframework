package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.specification.ConfigurationSpecification;

@Component
public class ConfigurationFacadeImpl extends AbstractFacade<Configuration, ConfigurationDto> implements ConfigurationFacade {

	private static final Class<Configuration> entityClass = Configuration.class;
	private static final Class<ConfigurationDto> dtoClass = ConfigurationDto.class;
	
	@Autowired
	private ConfigurationService configurationService;

	@Override
	public ConfigurationDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public ConfigurationDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public ConfigurationDto create(ConfigurationDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public ConfigurationDto update(ConfigurationDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<ConfigurationDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, ConfigurationSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass, dtoClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public ConfigurationDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

	@Override
	public String get(String id, String defaultValue) throws Exception {
		String value = configurationService.get(id);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		} else {
			return value;
		}
	}

	@Override
	public boolean is(String id, boolean defaultValue) throws Exception {
		String value = configurationService.get(id);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		} else {
			return BooleanUtils.parseBoolean(value);
		}
	}
}
