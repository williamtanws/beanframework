package com.beanframework.console.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.beanframework.console.ConsoleWebConstants;

@Component
public class ConsoleLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Value(ConsoleWebConstants.Path.CONSOLE)
  private String PATH_CONSOLE;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    getRedirectStrategy().sendRedirect(request, response, PATH_CONSOLE);
  }
}
