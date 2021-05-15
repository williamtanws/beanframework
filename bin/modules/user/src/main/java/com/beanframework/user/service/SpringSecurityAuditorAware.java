package com.beanframework.user.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Auditor> {

  protected static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityAuditorAware.class);

  @Autowired
  private ModelService modelService;

  @Autowired
  private OverRiddenUser overRiddenUser;

  @Autowired
  private UserService userService;

  @Override
  public Optional<Auditor> getCurrentAuditor() {
    try {
      User user = null;

      if (overRiddenUser != null && overRiddenUser.getUserThreadMap() != null
          && overRiddenUser.getUserThreadMap().get(Thread.currentThread().getName()) != null) {
        user = overRiddenUser.getUserThreadMap().get(Thread.currentThread().getName());
      } else {
        user = userService.getCurrentUser();
      }

      if (user != null) {
        if (user.getUuid() != null) {
          Auditor auditor = modelService.findOneByUuid(user.getUuid(), Auditor.class);

          if (auditor != null) {
            return Optional.of(auditor);
          }
        }
      }

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }

    return Optional.empty();
  }

}
