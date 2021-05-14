package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.internationalization.domain.Currency;

public class CurrencyDtoConverter extends AbstractDtoConverter<Currency, CurrencyDto>
    implements DtoConverter<Currency, CurrencyDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(CurrencyDtoConverter.class);

  @Override
  public CurrencyDto convert(Currency source) throws ConverterException {
    return super.convert(source, new CurrencyDto());
  }

}
