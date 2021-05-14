package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.user.domain.Address;

public class AddressDtoConverter extends AbstractDtoConverter<Address, AddressDto>
    implements DtoConverter<Address, AddressDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(AddressDtoConverter.class);

  @Override
  public AddressDto convert(Address source) throws ConverterException {
    return super.convert(source, new AddressDto());
  }
}
