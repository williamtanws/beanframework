package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.backoffice.data.BackofficeConfigurationDto;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;

@Service
public class BackofficeConfigurationFacadeImpl implements BackofficeConfigurationFacade {

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private ModelService modelService;

	@Override
	public BackofficeConfigurationDto findOneDtoById(String id) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, id);

		return modelService.getDto(configurationService.findOneEntityByProperties(properties), BackofficeConfigurationDto.class);
	}

}
