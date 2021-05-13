package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CountryDtoConverter;
import com.beanframework.core.converter.populator.CountryPopulator;
import com.beanframework.core.data.CountryDto;

@Configuration
public class CountryDtoConfig {

  @Autowired
  public CountryPopulator countryPopulator;

  @Bean
  public CountryDtoConverter countryDtoConverter() {
    CountryDtoConverter converter = new CountryDtoConverter();
    converter.addPopulator(countryPopulator);
    return converter;
  }

  @Bean
  public ConverterMapping countryDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(countryDtoConverter());
    mapping.setTypeCode(CountryDto.class.getSimpleName());

    return mapping;
  }
}
