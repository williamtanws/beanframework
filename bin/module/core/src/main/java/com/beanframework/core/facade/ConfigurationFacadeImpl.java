package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.configuration.specification.ConfigurationSpecification;
import com.beanframework.core.converter.populator.ConfigurationFullPopulator;
import com.beanframework.core.data.ConfigurationDto;

@Component
public class ConfigurationFacadeImpl implements ConfigurationFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private ConfigurationFullPopulator configurationFullPopulator;

	@Override
	public ConfigurationDto findOneByUuid(UUID uuid) throws Exception {
		Configuration entity = modelService.findOneByUuid(uuid, Configuration.class);

		return modelService.getDto(entity, ConfigurationDto.class, new DtoConverterContext(configurationFullPopulator));
	}

	@Override
	public ConfigurationDto findOneProperties(Map<String, Object> properties) throws Exception {
		Configuration entity = modelService.findOneByProperties(properties, Configuration.class);

		return modelService.getDto(entity, ConfigurationDto.class, new DtoConverterContext(configurationFullPopulator));
	}

	@Override
	public ConfigurationDto create(ConfigurationDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public ConfigurationDto update(ConfigurationDto model) throws BusinessException {
		return save(model);
	}

	public ConfigurationDto save(ConfigurationDto dto) throws BusinessException {
		try {
			Configuration entity = modelService.getEntity(dto, Configuration.class);
			entity = modelService.saveEntity(entity, Configuration.class);

			return modelService.getDto(entity, ConfigurationDto.class, new DtoConverterContext(configurationFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Configuration.class);
	}

	@Override
	public Page<ConfigurationDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Configuration> page = modelService.findPage(ConfigurationSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Configuration.class);

		List<ConfigurationDto> dtos = modelService.getDto(page.getContent(), ConfigurationDto.class, new DtoConverterContext(configurationFullPopulator));
		return new PageImpl<ConfigurationDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Configuration.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = configurationService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Configuration) {

				entityObject[0] = modelService.getDto(entityObject[0], ConfigurationDto.class, new DtoConverterContext(configurationFullPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return configurationService.findCountHistory(dataTableRequest);
	}

	@Override
	public ConfigurationDto createDto() throws Exception {
		Configuration configuration = modelService.create(Configuration.class);
		return modelService.getDto(configuration, ConfigurationDto.class, new DtoConverterContext(configurationFullPopulator));
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
