package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.ImexDto;
import com.beanframework.imex.domain.Imex;

public class ImexDtoConverter extends AbstractDtoConverter<Imex, ImexDto>
    implements DtoConverter<Imex, ImexDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(ImexDtoConverter.class);

  @Override
  public ImexDto convert(Imex source) throws ConverterException {
    return super.convert(source, new ImexDto());
  }

}
