package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateDtoConverter extends AbstractDtoConverter<DynamicFieldTemplate, DynamicFieldTemplateDto> implements DtoConverter<DynamicFieldTemplate, DynamicFieldTemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplateDtoConverter.class);

	@Override
	public DynamicFieldTemplateDto convert(DynamicFieldTemplate source) throws ConverterException {
		return super.convert(source, new DynamicFieldTemplateDto());
	}

}
