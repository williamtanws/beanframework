package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.ConfigurationDto;

public class ConfigurationPopulator extends AbstractPopulator<Configuration, ConfigurationDto> implements Populator<Configuration, ConfigurationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(ConfigurationPopulator.class);

	@Override
	public void populate(Configuration source, ConfigurationDto target) throws PopulatorException {
		populateGeneric(source, target);
		target.setValue(source.getValue() != null ? HtmlUtils.htmlEscape(source.getValue()) : null);
	}

}
