package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldDtoConverter extends AbstractDtoConverter<DynamicField, DynamicFieldDto>
    implements DtoConverter<DynamicField, DynamicFieldDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldDtoConverter.class);

  @Override
  public DynamicFieldDto convert(DynamicField source) throws ConverterException {
    return super.convert(source, new DynamicFieldDto());
  }

}
