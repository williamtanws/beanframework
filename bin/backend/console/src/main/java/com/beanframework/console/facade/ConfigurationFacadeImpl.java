package com.beanframework.console.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.console.data.ConfigurationDto;
import com.beanframework.console.data.ConfigurationSearch;
import com.beanframework.console.data.ConfigurationSpecification;

@Component
public class ConfigurationFacadeImpl implements ConfigurationFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private ConfigurationService configurationService;

	@Override
	public Page<ConfigurationDto> findPage(ConfigurationSearch search, PageRequest pageable) throws Exception {
		Page<Configuration> page = configurationService.findEntityPage(search.toString(), ConfigurationSpecification.findByCriteria(search), pageable);
		List<ConfigurationDto> dtos = modelService.getDto(page.getContent(), ConfigurationDto.class);
		return new PageImpl<ConfigurationDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public ConfigurationDto findOneByUuid(UUID uuid) throws Exception {
		Configuration entity = configurationService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, ConfigurationDto.class);
	}

	@Override
	public ConfigurationDto findOneProperties(Map<String, Object> properties) throws Exception {
		Configuration entity = configurationService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, ConfigurationDto.class);
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
			entity = (Configuration) configurationService.saveEntity(entity);

			return modelService.getDto(entity, ConfigurationDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		configurationService.deleteByUuid(uuid);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = configurationService.findHistoryByUuid(uuid, firstResult, maxResults);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], ConfigurationDto.class);
		}

		return revisions;
	}

	@Override
	public List<ConfigurationDto> findAllDtoConfigurations() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(ConfigurationDto.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(configurationService.findEntityBySorts(sorts), ConfigurationDto.class);
	}
}
