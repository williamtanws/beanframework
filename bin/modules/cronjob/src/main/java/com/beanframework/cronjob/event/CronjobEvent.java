package com.beanframework.cronjob.event;

import com.beanframework.common.event.AbstractEvent;

public class CronjobEvent extends AbstractEvent {

  /**
   * 
   */
  private static final long serialVersionUID = -1880790360812059230L;

  public CronjobEvent(Object source, String message) {
    super(source, message);
  }
}
