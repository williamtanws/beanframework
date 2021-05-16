package com.beanframework.console.security;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import com.beanframework.common.service.ModelService;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;
import com.beanframework.user.service.UserService;

@Component
public class ConsoleLogoutHandler implements LogoutHandler {

  protected static final Logger LOGGER = LoggerFactory.getLogger(ConsoleLogoutHandler.class);

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private UserService userService;

  @Autowired
  private ModelService modelService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    try {
      User user = userService.getCurrentUser();
      user.getParameters().put(UserConstants.LOGOUT_LAST_DATE,
          UserConstants.PARAMETER_DATE_FORMAT.format(new Date()));
      modelService.saveEntity(user);

      applicationEventPublisher.publishEvent(new AuthenticationEvent(authentication.getPrincipal(),
          LogentryType.LOGOUT, "ID=" + user.getId()));
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }

  }
}
