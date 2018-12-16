package com.beanframework.configuration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;

@Component
public class ConfigurationFacadeImpl implements ConfigurationFacade {
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private ConfigurationService configurationService;

	@Override
	public Page<Configuration> page(Configuration configuration, int page, int size, Direction direction,
			String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		Page<Configuration> configurationPage = configurationService.page(configuration, pageRequest);
		
		List<Configuration> content = modelService.getDto(configurationPage.getContent());
		return new PageImpl<Configuration>(content, configurationPage.getPageable(), configurationPage.getTotalElements());
	}
}
