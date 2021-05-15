package com.beanframework.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.beanframework.user.service.OverRiddenUser;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class CoreAuditorConfig {

  @Bean
  public OverRiddenUser overRideUser() {
    OverRiddenUser obj = new OverRiddenUser();
    return obj;
    // Singleton implementation can be done her if required
  }
}
