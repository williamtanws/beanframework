package com.beanframework.configuration.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.configuration.ConfigurationConstants;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;

@Component
public class DeleteConfigurationValidator implements Validator {

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
		
		UUID uuid = (UUID) target;

		Configuration userGroup = configurationService.findByUuid(uuid);
		if(userGroup == null) {
			errors.reject(Configuration.ID, localMessageService.getMessage(ConfigurationConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
