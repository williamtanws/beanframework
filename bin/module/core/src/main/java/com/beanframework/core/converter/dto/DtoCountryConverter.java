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
import com.beanframework.core.data.CountryDto;
import com.beanframework.internationalization.domain.Country;

public class DtoCountryConverter extends AbstractDtoConverter<Country, CountryDto> implements DtoConverter<Country, CountryDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCountryConverter.class);

	@Override
	public CountryDto convert(Country source, DtoConverterContext context) throws ConverterException {
		try {
			CountryDto target = new CountryDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<CountryDto> convert(List<Country> sources, DtoConverterContext context) throws ConverterException {
		List<CountryDto> convertedList = new ArrayList<CountryDto>();
		try {
			for (Country source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
