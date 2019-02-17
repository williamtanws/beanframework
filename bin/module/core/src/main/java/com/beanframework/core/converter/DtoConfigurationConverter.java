package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.ConfigurationDto;

public class DtoConfigurationConverter implements DtoConverter<Configuration, ConfigurationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoConfigurationConverter.class);

	@Autowired
	private ModelService modelService;

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

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());

		try {
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
