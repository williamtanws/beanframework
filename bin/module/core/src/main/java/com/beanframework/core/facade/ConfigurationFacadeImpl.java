package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.specification.ConfigurationSpecification;

@Component
public class ConfigurationFacadeImpl implements ConfigurationFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private ConfigurationService configurationService;

	@Override
	public ConfigurationDto findOneByUuid(UUID uuid) throws Exception {
		Configuration entity = configurationService.findOneEntityByUuid(uuid);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, ConfigurationDto.class);
	}

	@Override
	public ConfigurationDto findOneProperties(Map<String, Object> properties) throws Exception {
		Configuration entity = configurationService.findOneEntityByProperties(properties);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, ConfigurationDto.class);
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

			InterceptorContext context = new InterceptorContext();
			context.setInitializeCollection(true);
			return modelService.getDto(entity, context, ConfigurationDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		configurationService.deleteByUuid(uuid);
	}

	@Override
	public Page<ConfigurationDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Configuration> page = configurationService.findEntityPage(dataTableRequest, ConfigurationSpecification.getSpecification(dataTableRequest));
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<ConfigurationDto> dtos = modelService.getDto(page.getContent(), context, ConfigurationDto.class);
		return new PageImpl<ConfigurationDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return configurationService.count();
	}

	@Override
	public List<ConfigurationDto> findAllDtoConfigurations() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Configuration.CREATED_DATE, Sort.Direction.DESC);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		return modelService.getDto(configurationService.findEntityBySorts(sorts, false), context, ConfigurationDto.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = configurationService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Configuration) {
				
				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(true);
				entityObject[0] = modelService.getDto(entityObject[0], context, ConfigurationDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return configurationService.findCountHistory(dataTableRequest);
	}
}
