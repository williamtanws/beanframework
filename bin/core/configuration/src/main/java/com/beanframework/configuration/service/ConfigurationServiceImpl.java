package com.beanframework.configuration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.domain.ConfigurationSpecification;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<Configuration> page(Configuration configuration, Pageable pageable) {
		Page<Configuration> page = modelService.findPage(ConfigurationSpecification.findByCriteria(configuration),
				pageable, Configuration.class);
		return page;
	}
}
