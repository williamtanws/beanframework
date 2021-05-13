package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EnumerationEntityConverter;
import com.beanframework.enumuration.domain.Enumeration;

@Configuration
public class EnumerationEntityConfig {

  @Autowired
  public EnumerationEntityConverter entityEnumerationConverter;

  @Bean
  public ConverterMapping entityEnumerationConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(entityEnumerationConverter);
    mapping.setTypeCode(Enumeration.class.getSimpleName());

    return mapping;
  }
}
