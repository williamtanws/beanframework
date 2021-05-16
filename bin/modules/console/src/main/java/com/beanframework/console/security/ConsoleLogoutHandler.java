package com.beanframework.console.security;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.logentry.LogentryType;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;

@Component
public class ConsoleLogoutHandler implements LogoutHandler {

  protected static final Logger LOGGER = LoggerFactory.getLogger(ConsoleLogoutHandler.class);

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private ModelService modelService;

  public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ssa");
  public static final String LOGOUT_LAST_DATE = "logout.last.date";

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    user.getParameters().put(LOGOUT_LAST_DATE, dateFormat.format(new Date()));
    try {
      modelService.saveEntity(user);
    } catch (BusinessException e) {
      LOGGER.error(e.getMessage(), e);
    }
    applicationEventPublisher.publishEvent(new AuthenticationEvent(authentication.getPrincipal(),
        LogentryType.LOGOUT, "ID=" + user.getId()));

  }
}
