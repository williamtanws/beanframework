package com.beanframework.console.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;

@Component
public class ConsoleLogoutHandler implements LogoutHandler {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    applicationEventPublisher.publishEvent(new AuthenticationEvent(authentication.getPrincipal(),
        LogentryType.LOGOUT, "ID=" + user.getId()));

  }
}
