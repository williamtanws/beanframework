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
import com.beanframework.core.data.LanguageDto;
import com.beanframework.internationalization.domain.Language;

public class DtoLanguageConverter extends AbstractDtoConverter<Language, LanguageDto> implements DtoConverter<Language, LanguageDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoLanguageConverter.class);

	@Override
	public LanguageDto convert(Language source, DtoConverterContext context) throws ConverterException {
		try {
			LanguageDto target = new LanguageDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<LanguageDto> convert(List<Language> sources, DtoConverterContext context) throws ConverterException {
		List<LanguageDto> convertedList = new ArrayList<LanguageDto>();
		for (Language source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}
}
