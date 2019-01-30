package com.beanframework.console.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.ConfigurationDto;

public class DtoConfigurationConverter implements DtoConverter<Configuration, ConfigurationDto> {

	@Override
	public ConfigurationDto convert(Configuration source, ModelAction action) throws ConverterException {
		return convert(source, new ConfigurationDto(), action);
	}

	public List<ConfigurationDto> convert(List<Configuration> sources, ModelAction action) throws ConverterException {

		List<ConfigurationDto> convertedList = new ArrayList<ConfigurationDto>();
		try {
			for (Configuration source : sources) {
				convertedList.add(convert(source, action));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private ConfigurationDto convert(Configuration source, ConfigurationDto prototype, ModelAction action) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());

		return prototype;
	}

}
