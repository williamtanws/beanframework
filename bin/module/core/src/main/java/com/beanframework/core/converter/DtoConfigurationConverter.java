package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.ConfigurationDto;

public class DtoConfigurationConverter extends AbstractDtoConverter<Configuration, ConfigurationDto> implements DtoConverter<Configuration, ConfigurationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoConfigurationConverter.class);

	@Override
	public ConfigurationDto convert(Configuration source, DtoConverterContext context) throws ConverterException {
		return convert(source, new ConfigurationDto(), context);
	}

	public List<ConfigurationDto> convert(List<Configuration> sources, DtoConverterContext context) throws ConverterException {

		List<ConfigurationDto> convertedList = new ArrayList<ConfigurationDto>();
		try {
			for (Configuration source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private ConfigurationDto convert(Configuration source, ConfigurationDto prototype, DtoConverterContext context) throws ConverterException {
		try {
			convertCommonProperties(source, prototype, context);

			prototype.setValue(source.getValue());

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
