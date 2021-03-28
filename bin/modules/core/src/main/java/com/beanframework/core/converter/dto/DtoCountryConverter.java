package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.CountryDto;
import com.beanframework.internationalization.domain.Country;

public class DtoCountryConverter extends AbstractDtoConverter<Country, CountryDto> implements DtoConverter<Country, CountryDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCountryConverter.class);

	@Override
	public CountryDto convert(Country source) throws ConverterException {
		return super.convert(source, new CountryDto());
	}

}
