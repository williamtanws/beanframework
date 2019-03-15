package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.language.domain.Language;

public class DtoLanguageConverter extends AbstractDtoConverter<Language, LanguageDto> implements DtoConverter<Language, LanguageDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoLanguageConverter.class);

	@Override
	public LanguageDto convert(Language source, DtoConverterContext context) throws ConverterException {
		return convert(source, new LanguageDto(), context);
	}

	public List<LanguageDto> convert(List<Language> sources, DtoConverterContext context) throws ConverterException {
		List<LanguageDto> convertedList = new ArrayList<LanguageDto>();
		for (Language source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private LanguageDto convert(Language source, LanguageDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertGeneric(source, prototype, context);

			prototype.setName(source.getName());
			prototype.setSort(source.getSort());
			prototype.setActive(source.getActive());

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
