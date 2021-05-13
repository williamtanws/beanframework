package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.user.domain.Customer;

public class CustomerDtoConverter extends AbstractDtoConverter<Customer, CustomerDto>
    implements DtoConverter<Customer, CustomerDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(CustomerDtoConverter.class);

  @Override
  public CustomerDto convert(Customer source) throws ConverterException {
    return super.convert(source, new CustomerDto());
  }

}
