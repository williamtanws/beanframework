package com.beanframework.backoffice.security;

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
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.common.service.ModelService;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;
import com.beanframework.user.service.UserService;

@Component
public class BackofficeLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  protected static final Logger LOGGER =
      LoggerFactory.getLogger(BackofficeLoginSuccessHandler.class);

  @Value(BackofficeWebConstants.Path.BACKOFFICE)
  private String PATH_BACKOFFICE;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private UserService userService;

  @Autowired
  private ModelService modelService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    try {
      User user = userService.getCurrentUser();
      user.getParameters().put(UserConstants.LOGIN_LAST_DATE,
          UserConstants.PARAMETER_DATE_FORMAT.format(new Date()));
      modelService.saveEntity(user);
      userService.updateCurrentUserSession();

      applicationEventPublisher.publishEvent(new AuthenticationEvent(authentication.getPrincipal(),
          LogentryType.LOGIN, "ID=" + user.getId()));

      getRedirectStrategy().sendRedirect(request, response, PATH_BACKOFFICE);
    } catch (Exception e) {
      throw new ServletException(e.getMessage(), e);
    }
  }
}
