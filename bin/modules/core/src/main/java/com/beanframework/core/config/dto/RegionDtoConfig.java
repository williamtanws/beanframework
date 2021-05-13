package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.RegionDtoConverter;
import com.beanframework.core.converter.populator.RegionPopulator;
import com.beanframework.core.data.RegionDto;

@Configuration
public class RegionDtoConfig {

  @Autowired
  public RegionPopulator regionPopulator;

  @Bean
  public RegionDtoConverter regionDtoConverter() {
    RegionDtoConverter converter = new RegionDtoConverter();
    converter.addPopulator(regionPopulator);
    return converter;
  }

  @Bean
  public ConverterMapping regionDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(regionDtoConverter());
    mapping.setTypeCode(RegionDto.class.getSimpleName());

    return mapping;
  }
}
