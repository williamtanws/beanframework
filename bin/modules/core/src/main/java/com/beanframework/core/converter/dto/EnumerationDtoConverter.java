package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationDtoConverter extends AbstractDtoConverter<Enumeration, EnumerationDto>
    implements DtoConverter<Enumeration, EnumerationDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(EnumerationDtoConverter.class);

  @Override
  public EnumerationDto convert(Enumeration source) throws ConverterException {
    return super.convert(source, new EnumerationDto());
  }
}
