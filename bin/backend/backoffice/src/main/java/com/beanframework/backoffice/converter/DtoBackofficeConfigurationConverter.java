package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.BackofficeConfigurationDto;

public class DtoBackofficeConfigurationConverter implements DtoConverter<Configuration, BackofficeConfigurationDto> {

	@Override
	public BackofficeConfigurationDto convert(Configuration source, ModelAction action) throws ConverterException {
		return convert(source, new BackofficeConfigurationDto(), action);
	}

	public List<BackofficeConfigurationDto> convert(List<Configuration> sources, ModelAction action) throws ConverterException {

		List<BackofficeConfigurationDto> convertedList = new ArrayList<BackofficeConfigurationDto>();
		try {
			for (Configuration source : sources) {
				convertedList.add(convert(source, action));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private BackofficeConfigurationDto convert(Configuration source, BackofficeConfigurationDto prototype, ModelAction action) {

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
