package com.beanframework.core.interceptor.vendor;

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
import com.beanframework.user.UserConstants;
import com.beanframework.user.VendorConstants;
import com.beanframework.user.domain.Vendor;
import com.beanframework.user.service.UserService;

public class VendorInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Vendor> {

  protected static Logger LOGGER = LoggerFactory.getLogger(VendorInitialDefaultsInterceptor.class);

  @Autowired
  private UserService userService;

  @Value(VendorConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Autowired
  private ConfigurationService configurationService;

  @Override
  public void onInitialDefaults(Vendor model, InterceptorContext context)
      throws InterceptorException {
    super.onInitialDefaults(model, context);
    model.setEnabled(true);
    model.setAccountNonExpired(true);
    model.setAccountNonLocked(true);
    model.setCredentialsNonExpired(true);

    try {
      String accountExpiryHoursDefault =
          configurationService.get(VendorConstants.CONFIGURATION_ACCOUNT_EXPIRY_HOURS_DEFAULT);
      if (StringUtils.isNotBlank(accountExpiryHoursDefault)) {
        DateTime date = new DateTime();
        date = date.plusHours(Integer.valueOf(accountExpiryHoursDefault));
        model.getParameters().put(UserConstants.ACCOUNT_EXPIRED_DATE,
            UserConstants.PARAMETER_DATE_FORMAT.format(date.toDate()));
      }

      String accountPasswordHoursDefault =
          configurationService.get(VendorConstants.CONFIGURATION_PASSWORD_EXPIRY_HOURS_DEFAULT);
      if (StringUtils.isNotBlank(accountPasswordHoursDefault)) {
        DateTime date = new DateTime();
        date = date.plusHours(Integer.valueOf(accountPasswordHoursDefault));
        model.getParameters().put(UserConstants.PASSWORD_EXPIRED_DATE,
            UserConstants.PARAMETER_DATE_FORMAT.format(date.toDate()));
      }

      String loginAttemptMaxDefault =
          configurationService.get(VendorConstants.CONFIGURATION_ACCOUNT_LOGIN_ATTEMPT_MAX_DEFAULT);
      model.getParameters().put(UserConstants.LOGIN_ATTEMPT_MAX, loginAttemptMaxDefault);

      userService.generateUserAttribute(model, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new InterceptorException(e.getMessage(), e);
    }
  }

}
