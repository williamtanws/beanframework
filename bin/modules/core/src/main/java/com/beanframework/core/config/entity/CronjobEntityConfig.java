package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.CronjobEntityConverter;
import com.beanframework.cronjob.domain.Cronjob;

@Configuration
public class CronjobEntityConfig {

  @Autowired
  public CronjobEntityConverter cronjobEntityConverter;

  @Bean
  public ConverterMapping cronjobEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(cronjobEntityConverter);
    mapping.setTypeCode(Cronjob.class.getSimpleName());

    return mapping;
  }
}
