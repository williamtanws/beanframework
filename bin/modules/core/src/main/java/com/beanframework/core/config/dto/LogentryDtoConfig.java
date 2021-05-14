package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.LogentryDtoConverter;
import com.beanframework.core.converter.populator.LogentryPopulator;
import com.beanframework.core.data.LogentryDto;

@Configuration
public class LogentryDtoConfig {

  @Autowired
  public LogentryPopulator logentryPopulator;

  @Bean
  public LogentryDtoConverter logentryDtoConverter() {
    LogentryDtoConverter converter = new LogentryDtoConverter();
    converter.addPopulator(logentryPopulator);
    return converter;
  }

  @Bean
  public ConverterMapping logentryDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(logentryDtoConverter());
    mapping.setTypeCode(LogentryDto.class.getSimpleName());

    return mapping;
  }
}
