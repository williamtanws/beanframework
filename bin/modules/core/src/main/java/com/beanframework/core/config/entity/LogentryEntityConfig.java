package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.LogentryEntityConverter;
import com.beanframework.logentry.domain.Logentry;

@Configuration
public class LogentryEntityConfig {

  @Autowired
  public LogentryEntityConverter logentryEntityConverter;

  @Bean
  public ConverterMapping logentryEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(logentryEntityConverter);
    mapping.setTypeCode(Logentry.class.getSimpleName());

    return mapping;
  }
}
