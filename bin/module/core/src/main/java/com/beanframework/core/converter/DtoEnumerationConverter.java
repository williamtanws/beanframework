package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.enumuration.domain.Enumeration;

public class DtoEnumerationConverter extends AbstractDtoConverter<Enumeration, EnumerationDto> implements DtoConverter<Enumeration, EnumerationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoEnumerationConverter.class);

	@Override
	public EnumerationDto convert(Enumeration source, DtoConverterContext context) throws ConverterException {
		return convert(source, new EnumerationDto(), context);
	}

	public List<EnumerationDto> convert(List<Enumeration> sources, DtoConverterContext context) throws ConverterException {
		List<EnumerationDto> convertedList = new ArrayList<EnumerationDto>();
		for (Enumeration source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private EnumerationDto convert(Enumeration source, EnumerationDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertGeneric(source, prototype, context);

			prototype.setName(source.getName());
			prototype.setSort(source.getSort());

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}
