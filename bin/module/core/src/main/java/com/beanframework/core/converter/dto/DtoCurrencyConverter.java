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
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.internationalization.domain.Currency;

public class DtoCurrencyConverter extends AbstractDtoConverter<Currency, CurrencyDto> implements DtoConverter<Currency, CurrencyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCurrencyConverter.class);

	@Override
	public CurrencyDto convert(Currency source, DtoConverterContext context) throws ConverterException {
		try {
			CurrencyDto target = new CurrencyDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<CurrencyDto> convert(List<Currency> sources, DtoConverterContext context) throws ConverterException {
		List<CurrencyDto> convertedList = new ArrayList<CurrencyDto>();
		try {
			for (Currency source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
