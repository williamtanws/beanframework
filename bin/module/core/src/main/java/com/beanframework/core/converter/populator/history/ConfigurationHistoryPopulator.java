package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.ConfigurationDto;

@Component
public class ConfigurationHistoryPopulator extends AbstractPopulator<Configuration, ConfigurationDto> implements Populator<Configuration, ConfigurationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(ConfigurationHistoryPopulator.class);

	@Override
	public void populate(Configuration source, ConfigurationDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setValue(source.getValue());
	}

}
