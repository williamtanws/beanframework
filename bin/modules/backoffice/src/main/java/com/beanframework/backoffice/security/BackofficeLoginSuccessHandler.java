package com.beanframework.backoffice.security;

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
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;

@Component
public class BackofficeLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Value(BackofficeWebConstants.Path.BACKOFFICE)
  private String PATH_BACKOFFICE;

  // private RequestCache requestCache = new HttpSessionRequestCache();

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    // Redirect to requested path
    // DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request,
    // response);
    //
    // if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
    // if (StringUtils.isNotBlank(savedRequest.getQueryString())) {
    // getRedirectStrategy().sendRedirect(request, response, savedRequest.getRequestURL() + "?" +
    // savedRequest.getQueryString());
    // } else {
    // getRedirectStrategy().sendRedirect(request, response, savedRequest.getRequestURL());
    // }
    // } else {
    // getRedirectStrategy().sendRedirect(request, response, PATH_BACKOFFICE);
    // }
    User user = (User) authentication.getPrincipal();
    applicationEventPublisher.publishEvent(new AuthenticationEvent(authentication.getPrincipal(),
        LogentryType.LOGIN, "Logon ID=" + user.getId()));

    getRedirectStrategy().sendRedirect(request, response, PATH_BACKOFFICE);
  }
}
