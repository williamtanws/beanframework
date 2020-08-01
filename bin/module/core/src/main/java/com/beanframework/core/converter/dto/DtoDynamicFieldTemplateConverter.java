package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DtoDynamicFieldTemplateConverter extends AbstractDtoConverter<DynamicFieldTemplate, DynamicFieldTemplateDto> implements DtoConverter<DynamicFieldTemplate, DynamicFieldTemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldTemplateConverter.class);

	@Override
	public DynamicFieldTemplateDto convert(DynamicFieldTemplate source, DtoConverterContext context) throws ConverterException {
		try {
			DynamicFieldTemplateDto target = new DynamicFieldTemplateDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<DynamicFieldTemplateDto> convert(List<DynamicFieldTemplate> sources, DtoConverterContext context) throws ConverterException {
		List<DynamicFieldTemplateDto> convertedList = new ArrayList<DynamicFieldTemplateDto>();
		for (DynamicFieldTemplate source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
