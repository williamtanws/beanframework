package com.beanframework.cronjob.envers;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPostUpdateEventListenerImpl;
import org.hibernate.event.spi.PostUpdateEvent;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum;

public class CronjobEnversPostUpdateEventListenerImpl extends EnversPostUpdateEventListenerImpl {

  /**
   * 
   */
  private static final long serialVersionUID = -2150028234331345621L;

  public CronjobEnversPostUpdateEventListenerImpl(EnversService enversService) {
    super(enversService);
  }

  @Override
  public void onPostUpdate(PostUpdateEvent event) {
    if (event.getEntity() instanceof Cronjob) {
      Cronjob cronjob = (Cronjob) event.getEntity();
      if (cronjob.getStatus() != null && cronjob.getStatus().equals(CronjobEnum.Status.RUNNING)
          && cronjob.getLastModifiedBy() == null)
        return;
    }

    super.onPostUpdate(event);
  }

}
