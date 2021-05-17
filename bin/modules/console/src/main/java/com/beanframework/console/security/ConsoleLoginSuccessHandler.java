package com.beanframework.console.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.user.service.UserService;

@Component
public class ConsoleLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  protected static final Logger LOGGER = LoggerFactory.getLogger(ConsoleLoginSuccessHandler.class);

  @Value(ConsoleWebConstants.Path.CONSOLE)
  private String PATH_CONSOLE;

  @Autowired
  private UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    userService.loginSuccessHandler();

    getRedirectStrategy().sendRedirect(request, response, PATH_CONSOLE);
  }
}
