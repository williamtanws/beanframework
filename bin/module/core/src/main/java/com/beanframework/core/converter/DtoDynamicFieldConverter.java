package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DtoDynamicFieldConverter extends AbstractDtoConverter<DynamicField, DynamicFieldDto> implements DtoConverter<DynamicField, DynamicFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldConverter.class);

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
		try {

			convertCommonProperties(source, prototype, context);

			prototype.setName(source.getName());
			prototype.setRequired(source.getRequired());
			prototype.setRule(source.getRule());
			prototype.setType(source.getType());
			prototype.setLabel(source.getLabel());
			prototype.setGrid(source.getGrid());

			if (context.isFetchable(DynamicField.class, DynamicField.LANGUAGE))
				prototype.setLanguage(modelService.getDto(source.getLanguage(), LanguageDto.class));

			if (context.isFetchable(DynamicField.class, DynamicField.ENUMERATIONS))
				prototype.setEnumerations(modelService.getDto(source.getEnumerations(), EnumerationDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
