package com.beanframework.backoffice.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.backoffice.data.BackofficeConfigurationDto;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.service.ConfigurationService;

@Service
public class BackofficeConfigurationFacadeImpl implements BackofficeConfigurationFacade {
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private ModelService modelService;

	@Override
	public BackofficeConfigurationDto findOneDtoById(String id) throws Exception {
		return modelService.getDto(configurationService.findOneEntityById(id), BackofficeConfigurationDto.class);
	}

}
