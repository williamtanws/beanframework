package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.internationalization.domain.Language;

public class DtoLanguageConverter extends AbstractDtoConverter<Language, LanguageDto> implements DtoConverter<Language, LanguageDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoLanguageConverter.class);

	@Override
	public LanguageDto convert(Language source) throws ConverterException {
		return super.convert(source, new LanguageDto());
	}
}
