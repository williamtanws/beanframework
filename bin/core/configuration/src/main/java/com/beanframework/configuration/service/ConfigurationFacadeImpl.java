package com.beanframework.configuration.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;

@Component
public class ConfigurationFacadeImpl implements ConfigurationFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<Configuration> findDtoPage(Specification<Configuration> specification, PageRequest pageable) throws Exception {
		return modelService.findDtoPage(specification, pageable, Configuration.class);
	}

	@Override
	public Configuration create() throws Exception {
		return modelService.create(Configuration.class);
	}

	@Override
	public Configuration findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, Configuration.class);
	}

	@Override
	public Configuration createDto(Configuration model) throws BusinessException {
		return (Configuration) modelService.saveDto(model, Configuration.class);
	}

	@Override
	public Configuration updateDto(Configuration model) throws BusinessException {
		return (Configuration) modelService.saveDto(model, Configuration.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Configuration.class);
	}
	
	
}
