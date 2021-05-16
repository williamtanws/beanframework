package com.beanframework.console.security;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.facade.UserFacade;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.UserConstants;
import com.beanframework.user.event.AuthenticationEvent;
import com.beanframework.user.service.UserService;

@Component
public class ConsoleLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  protected static final Logger LOGGER = LoggerFactory.getLogger(ConsoleLoginSuccessHandler.class);

  @Value(ConsoleWebConstants.Path.CONSOLE)
  private String PATH_CONSOLE;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private UserFacade userFacade;

  @Autowired
  private UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    try {
      UserDto user = userFacade.getCurrentUser();
      user.getParameters().put(UserConstants.LOGIN_LAST_DATE,
          UserConstants.PARAMETER_DATE_FORMAT.format(new Date()));
      userFacade.update(user);
      userService.updateCurrentUserSession();

      applicationEventPublisher.publishEvent(new AuthenticationEvent(authentication.getPrincipal(),
          LogentryType.LOGIN, "ID=" + user.getId()));

      getRedirectStrategy().sendRedirect(request, response, PATH_CONSOLE);
    } catch (Exception e) {
      throw new ServletException(e.getMessage(), e);
    }
  }
}
