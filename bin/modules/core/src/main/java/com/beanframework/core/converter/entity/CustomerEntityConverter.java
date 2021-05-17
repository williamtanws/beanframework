package com.beanframework.core.converter.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.user.domain.Customer;

@Component
public class CustomerEntityConverter implements EntityConverter<CustomerDto, Customer> {

  @Autowired
  private ModelService modelService;

  @Autowired
  private UserEntityConverter userEntityConverter;

  @Override
  public Customer convert(CustomerDto source) throws ConverterException {

    try {
      Customer prototype;
      if (source.getUuid() != null) {
        prototype = modelService.findOneByUuid(source.getUuid(), Customer.class);
        prototype = (Customer) userEntityConverter.convertToEntity(source, prototype);

      } else {
        prototype = (Customer) userEntityConverter.convertToEntity(source,
            modelService.create(Customer.class));
      }

      // Additional property here if availably

      return prototype;

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }
}
