package com.beanframework.core.interceptor.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.EmployeeConstants;
import com.beanframework.user.domain.Employee;
import com.beanframework.user.service.UserService;

public class EmployeeLoadInterceptor extends AbstractLoadInterceptor<Employee> {

  protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeLoadInterceptor.class);

  @Autowired
  private UserService userService;

  @Value(EmployeeConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Override
  public void onLoad(Employee model, InterceptorContext context) throws InterceptorException {
    try {
      userService.generateUserAttribute(model, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new InterceptorException(e.getMessage(), e);
    }
  }

}
