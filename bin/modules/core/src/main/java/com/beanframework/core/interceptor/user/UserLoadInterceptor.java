package com.beanframework.core.interceptor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.user.CustomerConstants;
import com.beanframework.user.EmployeeConstants;
import com.beanframework.user.VendorConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

public class UserLoadInterceptor extends AbstractLoadInterceptor<User> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserLoadInterceptor.class);

  @Autowired
  private UserService userService;

  @Value(EmployeeConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String EMPLOYEE_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Value(CustomerConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String CUSTOMER_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Value(VendorConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String VENDOR_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Override
  public void onLoad(User model, InterceptorContext context) throws InterceptorException {

    try {
      if (model.getType().equals(EmployeeConstants.Discriminator.EMPLOYEE)) {
        userService.generateUserAttribute(model, EMPLOYEE_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
      } else if (model.getType().equals(CustomerConstants.Discriminator.CUSTOMER)) {
        userService.generateUserAttribute(model, CUSTOMER_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
      } else if (model.getType().equals(VendorConstants.Discriminator.VENDOR)) {
        userService.generateUserAttribute(model, VENDOR_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
      }
    } catch (Exception e) {
      throw new InterceptorException(e.getMessage(), e);
    }
  }
}
