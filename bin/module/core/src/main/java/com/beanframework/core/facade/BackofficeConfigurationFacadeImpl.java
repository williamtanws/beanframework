package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.core.data.BackofficeConfigurationDto;

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

		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(configurationService.findOneEntityByProperties(properties), context, BackofficeConfigurationDto.class);
	}

}
