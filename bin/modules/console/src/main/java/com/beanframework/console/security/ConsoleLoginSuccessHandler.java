package com.beanframework.console.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;

@Component
public class ConsoleLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Value(ConsoleWebConstants.Path.CONSOLE)
  private String PATH_CONSOLE;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    User user = (User) authentication.getPrincipal();
    applicationEventPublisher.publishEvent(new AuthenticationEvent(authentication.getPrincipal(),
        LogentryType.LOGIN, "ID=" + user.getId()));
    getRedirectStrategy().sendRedirect(request, response, PATH_CONSOLE);
  }
}
