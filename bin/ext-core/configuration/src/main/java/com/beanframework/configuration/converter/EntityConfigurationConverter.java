package com.beanframework.configuration.converter;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;

@Component
public class EntityConfigurationConverter implements Converter<Configuration, Configuration> {

	@Autowired
	private ConfigurationService configurationService;

	@Override
	public Configuration convert(Configuration source) {

		Optional<Configuration> prototype = null;
		if (source.getUuid() != null) {
			prototype = configurationService.findEntityByUuid(source.getUuid());
			if (prototype.isPresent() == false) {
				prototype = Optional.of(configurationService.create());
			}
		}
		else {
			prototype = Optional.of(configurationService.create());
		}

		return convert(source, prototype.get());
	}

	private Configuration convert(Configuration source, Configuration prototype) {

		prototype.setId(source.getId());
		prototype.setValue(source.getValue());
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
