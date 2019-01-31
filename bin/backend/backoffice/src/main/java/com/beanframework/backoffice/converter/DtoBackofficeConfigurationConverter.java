package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.BackofficeConfigurationDto;

public class DtoBackofficeConfigurationConverter implements DtoConverter<Configuration, BackofficeConfigurationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoBackofficeConfigurationConverter.class);

	@Autowired
	private ModelService modelService;

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

	private BackofficeConfigurationDto convert(Configuration source, BackofficeConfigurationDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());

		try {
			ModelAction disableInitialCollectionAction = new ModelAction();
			disableInitialCollectionAction.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionAction, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionAction, AuditorDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
