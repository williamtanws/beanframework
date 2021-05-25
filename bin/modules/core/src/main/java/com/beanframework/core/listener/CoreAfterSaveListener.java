package com.beanframework.core.listener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterSaveEvent;
import com.beanframework.common.registry.AfterSaveListener;
import com.beanframework.imex.domain.Imex;
import com.beanframework.imex.service.ImexService;
import com.beanframework.logentry.LogentryType;
import com.beanframework.logentry.event.LogentryEvent;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.AuditorService;
import com.beanframework.user.service.UserService;

@Component
public class CoreAfterSaveListener implements AfterSaveListener {
  protected static Logger LOGGER = LoggerFactory.getLogger(CoreAfterSaveListener.class);

  @Autowired
  private AuditorService auditorService;

  @Autowired
  private ImexService imexService;

  @Autowired
  private UserService userService;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void afterSave(final Object model, final AfterSaveEvent event) throws ListenerException {
    applicationEventPublisher.publishEvent(new LogentryEvent(model,
        event.getType() == AfterSaveEvent.CREATE ? LogentryType.CREATE : LogentryType.UPDATE));

    try {
      if (model instanceof User) {
        User user = (User) model;
        auditorService.saveEntityByUser(user);

        // For imex import
        if (StringUtils.isNotBlank(user.getProfilePicture())) {
          ClassPathResource resource = new ClassPathResource(user.getProfilePicture());
          userService.saveProfilePicture(user, resource.getInputStream());
        }

      } else if (model instanceof Imex) {
        Imex imex = (Imex) model;
        imexService.importExportMedia(imex);

      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new ListenerException(e.getMessage(), e);
    }

  }
}
