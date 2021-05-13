package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CronjobDtoConverter;
import com.beanframework.core.converter.populator.CronjobPopulator;
import com.beanframework.core.data.CronjobDto;

@Configuration
public class CronjobDtoConfig {

  @Autowired
  public CronjobPopulator cronjobPopulator;

  @Bean
  public CronjobDtoConverter cronjobDtoConverter() {
    CronjobDtoConverter converter = new CronjobDtoConverter();
    converter.addPopulator(cronjobPopulator);
    return converter;
  }

  @Bean
  public ConverterMapping cronjobDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(cronjobDtoConverter());
    mapping.setTypeCode(CronjobDto.class.getSimpleName());

    return mapping;
  }
}
