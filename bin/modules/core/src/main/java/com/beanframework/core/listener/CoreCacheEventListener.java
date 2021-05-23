package com.beanframework.core.listener;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreCacheEventListener implements CacheEventListener<Object, Object> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CacheEventListener.class);

  @Override
  public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
    LOGGER.debug("Event: " + event.getType() + " Key: " + event.getKey() + " old value: "
        + event.getOldValue() + " new value: " + event.getNewValue());
  }

}
