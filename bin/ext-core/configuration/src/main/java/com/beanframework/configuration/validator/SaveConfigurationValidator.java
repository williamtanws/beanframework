package com.beanframework.configuration.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.configuration.ConfigurationConstants;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;

@Component
public class SaveConfigurationValidator implements Validator {

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Configuration.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final Configuration configuration = (Configuration) target;

		if (configuration.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(configuration.getId())) {
				errors.reject(Configuration.ID,
						localMessageService.getMessage(ConfigurationConstants.Locale.ID_REQUIRED));
			} else {
				Configuration existsConfiguration = configurationService.findById(configuration.getId());
				if (existsConfiguration != null) {
					errors.reject(Configuration.ID,
							localMessageService.getMessage(ConfigurationConstants.Locale.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(configuration.getId())) {
				Configuration existsConfiguration = configurationService.findById(configuration.getId());
				if (existsConfiguration != null) {
					if (!configuration.getUuid().equals(existsConfiguration.getUuid())) {
						errors.reject(Configuration.ID,
								localMessageService.getMessage(ConfigurationConstants.Locale.ID_EXISTS));
					}
				}
			}
		}
	}

}
