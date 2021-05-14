package com.beanframework.core.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterRemoveEvent;
import com.beanframework.common.registry.AfterRemoveListener;
import com.beanframework.logentry.LogentryType;
import com.beanframework.logentry.event.LogentryEvent;

@Component
public class CoreAfterRemoveListener implements AfterRemoveListener {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void afterRemove(Object detachedModel, AfterRemoveEvent event) throws ListenerException {
    try {
      applicationEventPublisher.publishEvent(new LogentryEvent(detachedModel, LogentryType.DELETE));
    } catch (Exception e) {
      throw new ListenerException(e.getMessage(), e);
    }
  }
}
