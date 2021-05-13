package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotDtoConverter
    extends AbstractDtoConverter<DynamicFieldSlot, DynamicFieldSlotDto>
    implements DtoConverter<DynamicFieldSlot, DynamicFieldSlotDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldSlotDtoConverter.class);

  @Override
  public DynamicFieldSlotDto convert(DynamicFieldSlot source) throws ConverterException {
    return super.convert(source, new DynamicFieldSlotDto());
  }

}
