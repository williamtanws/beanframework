package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.ConfigurationDtoConverter;
import com.beanframework.core.converter.populator.ConfigurationPopulator;
import com.beanframework.core.data.ConfigurationDto;

@Configuration
public class ConfigurationDtoConfig {

  @Autowired
  public ConfigurationPopulator configurationPopulator;

  @Bean
  public ConfigurationDtoConverter configurationDtoConverter() {
    ConfigurationDtoConverter converter = new ConfigurationDtoConverter();
    converter.addPopulator(configurationPopulator);
    return converter;
  }

  @Bean
  public ConverterMapping configurationDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(configurationDtoConverter());
    mapping.setTypeCode(ConfigurationDto.class.getSimpleName());

    return mapping;
  }
}
