package com.beanframework.core.interceptor.user;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.user.CustomerConstants;
import com.beanframework.user.EmployeeConstants;
import com.beanframework.user.UserConstants;
import com.beanframework.user.VendorConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

public class UserInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<User> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserInitialDefaultsInterceptor.class);

  @Value(EmployeeConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String EMPLOYEE_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Value(CustomerConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String CUSTOMER_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Value(VendorConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String VENDOR_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Autowired
  private UserService userService;

  @Autowired
  private ConfigurationService configurationService;

  @Override
  public void onInitialDefaults(User model, InterceptorContext context)
      throws InterceptorException {

    super.onInitialDefaults(model, context);
    model.setEnabled(true);
    model.setAccountNonExpired(true);
    model.setAccountNonLocked(true);
    model.setCredentialsNonExpired(true);

    String accountExpiryHoursDefault = null;
    String accountPasswordHoursDefault = null;
    String loginAttemptMaxDefault = null;
    String dynamicFieldTemplate = null;

    try {
      if (model.getType().equals(EmployeeConstants.Discriminator.EMPLOYEE)) {
        accountExpiryHoursDefault =
            configurationService.get(EmployeeConstants.CONFIGURATION_ACCOUNT_EXPIRY_HOURS_DEFAULT);
        accountPasswordHoursDefault =
            configurationService.get(EmployeeConstants.CONFIGURATION_PASSWORD_EXPIRY_HOURS_DEFAULT);
        loginAttemptMaxDefault = configurationService
            .get(EmployeeConstants.CONFIGURATION_ACCOUNT_LOGIN_ATTEMPT_MAX_DEFAULT);
        dynamicFieldTemplate = EMPLOYEE_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;
      } else if (model.getType().equals(CustomerConstants.Discriminator.CUSTOMER)) {
        accountExpiryHoursDefault =
            configurationService.get(CustomerConstants.CONFIGURATION_ACCOUNT_EXPIRY_HOURS_DEFAULT);
        accountPasswordHoursDefault =
            configurationService.get(CustomerConstants.CONFIGURATION_PASSWORD_EXPIRY_HOURS_DEFAULT);
        loginAttemptMaxDefault = configurationService
            .get(CustomerConstants.CONFIGURATION_ACCOUNT_LOGIN_ATTEMPT_MAX_DEFAULT);
        dynamicFieldTemplate = CUSTOMER_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;
      } else if (model.getType().equals(VendorConstants.Discriminator.VENDOR)) {
        accountExpiryHoursDefault =
            configurationService.get(VendorConstants.CONFIGURATION_ACCOUNT_EXPIRY_HOURS_DEFAULT);
        accountPasswordHoursDefault =
            configurationService.get(VendorConstants.CONFIGURATION_PASSWORD_EXPIRY_HOURS_DEFAULT);
        loginAttemptMaxDefault = configurationService
            .get(VendorConstants.CONFIGURATION_ACCOUNT_LOGIN_ATTEMPT_MAX_DEFAULT);
        dynamicFieldTemplate = VENDOR_CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;
      }

      if (StringUtils.isNotBlank(accountExpiryHoursDefault)) {
        DateTime date = new DateTime();
        date = date.plusHours(Integer.valueOf(accountExpiryHoursDefault));
        model.getParameters().put(UserConstants.ACCOUNT_EXPIRED_DATE,
            UserConstants.PARAMETER_DATE_FORMAT.format(date.toDate()));
      }

      if (StringUtils.isNotBlank(accountPasswordHoursDefault)) {
        DateTime date = new DateTime();
        date = date.plusHours(Integer.valueOf(accountPasswordHoursDefault));
        model.getParameters().put(UserConstants.PASSWORD_EXPIRED_DATE,
            UserConstants.PARAMETER_DATE_FORMAT.format(date.toDate()));
      }

      if (StringUtils.isNotBlank(loginAttemptMaxDefault)) {
        model.getParameters().put(UserConstants.LOGIN_ATTEMPT_MAX, loginAttemptMaxDefault);
      }

      if (StringUtils.isNotBlank(dynamicFieldTemplate)) {
        userService.generateUserAttribute(model, dynamicFieldTemplate);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new InterceptorException(e.getMessage(), e);
    }
  }
}
