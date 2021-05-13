package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EmailEntityConverter;
import com.beanframework.email.domain.Email;

@Configuration
public class EmailEntityConfig {

  @Autowired
  public EmailEntityConverter emailEntityConverter;

  @Bean
  public ConverterMapping emailEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(emailEntityConverter);
    mapping.setTypeCode(Email.class.getSimpleName());

    return mapping;
  }
}
