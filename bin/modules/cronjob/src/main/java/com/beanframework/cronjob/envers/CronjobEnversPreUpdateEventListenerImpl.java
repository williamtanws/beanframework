package com.beanframework.cronjob.envers;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPreUpdateEventListenerImpl;
import org.hibernate.event.spi.PreUpdateEvent;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;

public class CronjobEnversPreUpdateEventListenerImpl extends EnversPreUpdateEventListenerImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1817622205039164452L;

  public CronjobEnversPreUpdateEventListenerImpl(EnversService enversService) {
    super(enversService);
  }

  @Override
  public boolean onPreUpdate(PreUpdateEvent event) {
    if (event.getEntity() instanceof Cronjob) {
      Cronjob cronjob = (Cronjob) event.getEntity();
      if (cronjob.getStatus() != null && cronjob.getStatus().equals(CronjobEnum.Status.RUNNING)
          && cronjob.getLastModifiedBy() == null)
        return false;
    }

    return super.onPreUpdate(event);
  }

}
