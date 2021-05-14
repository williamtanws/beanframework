package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.NotificationEntityConverter;
import com.beanframework.notification.domain.Notification;

@Configuration
public class NotificationEntityConfig {

  @Autowired
  public NotificationEntityConverter notificationEntityConverter;

  @Bean
  public ConverterMapping notificationEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(notificationEntityConverter);
    mapping.setTypeCode(Notification.class.getSimpleName());

    return mapping;
  }
}
