package com.beanframework.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.core.facade.NotificationFacade;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.event.CronjobEvent;
import com.beanframework.notification.NotificationConstants;

@Component
@EnableAsync
public class NotificationEventListener {

  protected static final Logger LOGGER = LoggerFactory.getLogger(NotificationEventListener.class);

  @Autowired
  private NotificationFacade notificationFacade;

  @EventListener
  @Async
  @CacheEvict(value = NotificationConstants.CACHE_NOTIFICATIONS, allEntries = true)
  public void cronjobEvent(CronjobEvent event) {

    try {
      NotificationDto dto = notificationFacade.createDto();
      dto.setMessage(event.getMessage());
      dto.setType(Cronjob.class.getSimpleName());
      dto.setParameters(event.getParameters());
      notificationFacade.save(dto);

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
