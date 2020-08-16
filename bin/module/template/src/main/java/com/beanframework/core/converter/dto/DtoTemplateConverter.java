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
import com.beanframework.core.data.TemplateDto;
import com.beanframework.template.domain.Template;

public class DtoTemplateConverter extends AbstractDtoConverter<Template, TemplateDto> implements DtoConverter<Template, TemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoTemplateConverter.class);

	@Override
	public TemplateDto convert(Template source, DtoConverterContext context) throws ConverterException {
		try {
			TemplateDto target = new TemplateDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<TemplateDto> convert(List<Template> sources, DtoConverterContext context) throws ConverterException {
		List<TemplateDto> convertedList = new ArrayList<TemplateDto>();
		for (Template source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}