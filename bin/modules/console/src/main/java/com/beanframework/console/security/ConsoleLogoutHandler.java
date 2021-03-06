package com.beanframework.console.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import com.beanframework.user.service.UserService;

@Component
public class ConsoleLogoutHandler implements LogoutHandler {

  protected static final Logger LOGGER = LoggerFactory.getLogger(ConsoleLogoutHandler.class);

  @Autowired
  private UserService userService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    userService.logoutSuccessHandler(request, response);

  }
}
