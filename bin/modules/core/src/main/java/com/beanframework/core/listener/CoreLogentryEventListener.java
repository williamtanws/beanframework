package com.beanframework.core.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.event.AbstractEvent;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.logentry.domain.Logentry;
import com.beanframework.logentry.event.LogentryEvent;
import com.beanframework.notification.domain.Notification;
import com.beanframework.user.event.AuthenticationEvent;

@Component
public class CoreLogentryEventListener implements ApplicationListener<AbstractEvent> {

  protected static final Logger LOGGER = LoggerFactory.getLogger(CoreLogentryEventListener.class);

  @Autowired
  private ModelService modelService;

  private static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, hh:mma");

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
          modelService.saveEntityByLegacyMode(entity, Logentry.class);

        } else {
          LogentryEvent logentryEvent = (LogentryEvent) event;
          if (logentryEvent.getSource() instanceof Cronjob == Boolean.FALSE
              && logentryEvent.getSource() instanceof Notification == Boolean.FALSE
              && logentryEvent.getSource() instanceof GenericEntity) {
            GenericEntity sourceEntity = (GenericEntity) logentryEvent.getSource();

            Logentry entity = new Logentry();
            entity.setType(logentryEvent.getType());
            entity.setCreatedDate(new Date(logentryEvent.getTimestamp()));
            if (sourceEntity.getUuid() != null) {
              entity.setMessage("uuid=" + sourceEntity.getUuid().toString() + ", id="
                  + sourceEntity.getId() + ", time=" + sdf.format(entity.getCreatedDate()));
            }
            modelService.saveEntityByLegacyMode(entity, Logentry.class);
          }
        }
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
