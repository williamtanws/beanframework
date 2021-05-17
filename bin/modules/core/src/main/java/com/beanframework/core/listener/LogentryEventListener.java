package com.beanframework.core.listener;

import java.text.MessageFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.event.AbstractEvent;
import com.beanframework.common.service.ModelService;
import com.beanframework.logentry.domain.Logentry;
import com.beanframework.logentry.event.LogentryEvent;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;
import com.beanframework.user.service.UserService;

@Component
public class LogentryEventListener implements ApplicationListener<AbstractEvent> {

  protected static final Logger LOGGER = LoggerFactory.getLogger(LogentryEventListener.class);

  @Autowired
  private UserService userService;

  @Autowired
  private ModelService modelService;

  @Override
  public void onApplicationEvent(AbstractEvent event) {

    try {
      if (event instanceof LogentryEvent) {

        if (event instanceof AuthenticationEvent) {
          AuthenticationEvent authenticationEvent = (AuthenticationEvent) event;
          Logentry entity = new Logentry();
          entity.setType(authenticationEvent.getType());
          entity.setCreatedDate(new Date(authenticationEvent.getTimestamp()));
          entity.setMessage(authenticationEvent.getMessage());
          modelService.saveEntityByLegacyMode(entity);

        } else {
          LogentryEvent logentryEvent = (LogentryEvent) event;
          if (logentryEvent.getSource() instanceof Logentry == Boolean.FALSE
              && logentryEvent.getSource() instanceof GenericEntity) {
            GenericEntity sourceEntity = (GenericEntity) logentryEvent.getSource();

            Logentry entity = new Logentry();
            entity.setType(logentryEvent.getType());
            entity.setCreatedDate(new Date(logentryEvent.getTimestamp()));
            if (sourceEntity.getUuid() != null) {
              String by = null;
              User currentUser = userService.getCurrentUser();
              if (currentUser != null) {
                by = currentUser.getId();
              } else {
                by = "system";
              }
              entity.setMessage(MessageFormat.format("{0}: uuid={1}, id={2}, by={3}",
                  logentryEvent.getSource().getClass().getSimpleName(),
                  sourceEntity.getUuid().toString(), sourceEntity.getId(), by));
            }
            modelService.saveEntityByLegacyMode(entity);
          }
        }
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
