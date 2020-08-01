package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.ConfigurationDto;

@Component
public class ConfigurationFullPopulator extends AbstractPopulator<Configuration, ConfigurationDto> implements Populator<Configuration, ConfigurationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(ConfigurationFullPopulator.class);

	@Override
	public void populate(Configuration source, ConfigurationDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setValue(source.getValue());
	}

}
