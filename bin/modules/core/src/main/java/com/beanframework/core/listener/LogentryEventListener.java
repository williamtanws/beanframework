package com.beanframework.core.listener;

import java.text.MessageFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.service.ModelService;
import com.beanframework.logentry.domain.Logentry;
import com.beanframework.logentry.event.LogentryEvent;
import com.beanframework.user.domain.User;
import com.beanframework.user.event.AuthenticationEvent;
import com.beanframework.user.service.UserService;

@Component
@EnableAsync
public class LogentryEventListener {

  protected static final Logger LOGGER = LoggerFactory.getLogger(LogentryEventListener.class);

  @Autowired
  private UserService userService;

  @Autowired
  private ModelService modelService;

  @EventListener
  @Async
  public void logentryEventEvent(LogentryEvent event) {

    try {
      if (event instanceof AuthenticationEvent) {
        AuthenticationEvent authenticationEvent = (AuthenticationEvent) event;
        Logentry entity = new Logentry();
        entity.setType(authenticationEvent.getType());
        entity.setCreatedDate(new Date(authenticationEvent.getTimestamp()));
        entity.setMessage(authenticationEvent.getMessage());
        modelService.saveEntityByLegacyMode(entity);

      } else {
        if (event.getSource() instanceof Logentry == Boolean.FALSE
            && event.getSource() instanceof GenericEntity) {
          GenericEntity sourceEntity = (GenericEntity) event.getSource();

          Logentry entity = new Logentry();
          entity.setType(event.getType());
          entity.setCreatedDate(new Date(event.getTimestamp()));
          if (sourceEntity.getUuid() != null) {
            String by = null;
            User currentUser = userService.getCurrentUser();
            if (currentUser != null) {
              by = currentUser.getId();
            } else {
              by = "system";
            }
            entity.setMessage(MessageFormat.format("{0}: uuid={1}, id={2}, by={3}",
                event.getSource().getClass().getSimpleName(), sourceEntity.getUuid().toString(),
                sourceEntity.getId(), by));
          }
          modelService.saveEntityByLegacyMode(entity);
        }
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
