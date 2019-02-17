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
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DtoDynamicFieldConverter implements DtoConverter<DynamicField, DynamicFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldDto convert(DynamicField source, DtoConverterContext context) throws ConverterException {
		return convert(source, new DynamicFieldDto(), context);
	}

	public List<DynamicFieldDto> convert(List<DynamicField> sources, DtoConverterContext context) throws ConverterException {
		List<DynamicFieldDto> convertedList = new ArrayList<DynamicFieldDto>();
		for (DynamicField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private DynamicFieldDto convert(DynamicField source, DynamicFieldDto prototype, DtoConverterContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setRequired(source.getRequired());
		prototype.setRule(source.getRule());
		prototype.setType(source.getType());
		prototype.setLabel(source.getLabel());

		try {
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));

			prototype.setLanguage(modelService.getDto(source.getLanguage(), LanguageDto.class));
			prototype.setEnumerations(modelService.getDto(source.getEnumerations(), EnumerationDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
