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
import com.beanframework.core.data.LogentryDto;
import com.beanframework.core.facade.LogentryFacade;
import com.beanframework.logentry.event.LogentryEvent;

@Component
public class CoreLogentryEventListener implements ApplicationListener<AbstractEvent> {

  protected static final Logger LOGGER = LoggerFactory.getLogger(CoreLogentryEventListener.class);

  @Autowired
  private LogentryFacade logentryFacade;

  private static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, hh:mma");

  @Override
  public void onApplicationEvent(AbstractEvent event) {

    try {
      if (event instanceof LogentryEvent) {
        LogentryEvent logentryEvent = (LogentryEvent) event;
        LogentryDto dto = logentryFacade.createDto();
        dto.setType(logentryEvent.getType());
        if (logentryEvent.getSource() instanceof GenericEntity) {
          GenericEntity entity = (GenericEntity) logentryEvent.getSource();
          dto.setMessage("uuid=" + entity.getUuid().toString() + ", id=" + entity.getId()
              + ", time=" + sdf.format(new Date(logentryEvent.getTimestamp())));
        }
        logentryFacade.create(dto);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
