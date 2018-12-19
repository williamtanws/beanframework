package com.beanframework.configuration.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.configuration.domain.Configuration;

public class DtoConfigurationConverter implements DtoConverter<Configuration, Configuration> {

	@Override
	public Configuration convert(Configuration source) throws ConverterException {
		return convert(source, new Configuration());
	}

	public List<Configuration> convert(List<Configuration> sources) throws ConverterException {

		List<Configuration> convertedList = new ArrayList<Configuration>();
		try {
			for (Configuration source : sources) {
				convertedList.add(convert(source));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), this);
		}
		return convertedList;
	}

	private Configuration convert(Configuration source, Configuration prototype) {
	
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
