package com.beanframework.configuration.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.ConfigurationConstants;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationValidateInterceptor implements ValidateInterceptor<Configuration> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public void onValidate(Configuration model) throws InterceptorException {

		try {
			if (model.getUuid() == null) {
				// Save new
				if (StringUtils.isBlank(model.getId())) {
					throw new InterceptorException(
							localMessageService.getMessage(ConfigurationConstants.Locale.ID_REQUIRED), this);
				} else {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(Configuration.ID, model.getId());
					boolean exists = modelService.existsByProperties(properties, Configuration.class);
					if (exists) {
						throw new InterceptorException(
								localMessageService.getMessage(ConfigurationConstants.Locale.ID_EXISTS), this);
					}
				}

			} else {
				// Update exists
				if (StringUtils.isNotBlank(model.getId())) {
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(Configuration.ID, model.getId());
					Configuration exists = modelService.findOneEntityByProperties(properties, Configuration.class);
					if (exists != null) {
						if (!model.getUuid().equals(exists.getUuid())) {
							throw new InterceptorException(
									localMessageService.getMessage(ConfigurationConstants.Locale.ID_EXISTS), this);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), this);
		}
	}

}
