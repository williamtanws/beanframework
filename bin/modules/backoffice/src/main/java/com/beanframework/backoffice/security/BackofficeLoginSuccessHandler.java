package com.beanframework.backoffice.security;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;

@Component
public class BackofficeLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  protected static final Logger LOGGER =
      LoggerFactory.getLogger(BackofficeLoginSuccessHandler.class);

  @Value(BackofficeWebConstants.Path.BACKOFFICE)
  private String PATH_BACKOFFICE;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private ModelService modelService;

  public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ssa");
  public static final String LOGIN_LAST_DATE = "login.last.date";

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    User user = (User) authentication.getPrincipal();
    user.getParameters().put(LOGIN_LAST_DATE, dateFormat.format(new Date()));
    try {
      modelService.saveEntity(user);
    } catch (BusinessException e) {
      LOGGER.error(e.getMessage(), e);
    }

    applicationEventPublisher.publishEvent(new AuthenticationEvent(authentication.getPrincipal(),
        LogentryType.LOGIN, "ID=" + user.getId()));

    getRedirectStrategy().sendRedirect(request, response, PATH_BACKOFFICE);
  }
}
