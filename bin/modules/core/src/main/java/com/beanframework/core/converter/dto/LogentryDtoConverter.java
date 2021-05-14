package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.LogentryDto;
import com.beanframework.logentry.domain.Logentry;

public class LogentryDtoConverter extends AbstractDtoConverter<Logentry, LogentryDto>
    implements DtoConverter<Logentry, LogentryDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(LogentryDtoConverter.class);

  @Override
  public LogentryDto convert(Logentry source) throws ConverterException {
    return super.convert(source, new LogentryDto());
  }
}
