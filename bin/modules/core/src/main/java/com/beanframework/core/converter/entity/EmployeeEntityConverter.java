package com.beanframework.core.converter.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.domain.Employee;

@Component
public class EmployeeEntityConverter implements EntityConverter<EmployeeDto, Employee> {

  @Autowired
  private ModelService modelService;

  @Autowired
  private UserEntityConverter userEntityConverter;

  @Override
  public Employee convert(EmployeeDto source) throws ConverterException {

    try {
      Employee prototype;
      if (source.getUuid() != null) {
        prototype = modelService.findOneByUuid(source.getUuid(), Employee.class);
        prototype = (Employee) userEntityConverter.convertToEntity(source, prototype);

      } else {
        prototype = (Employee) userEntityConverter.convertToEntity(source,
            modelService.create(Employee.class));
      }

      // Additional property here if availably

      return prototype;

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }
}
