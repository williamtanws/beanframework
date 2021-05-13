package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.CountryEntityConverter;
import com.beanframework.internationalization.domain.Country;

@Configuration
public class CountryEntityConfig {

  @Autowired
  public CountryEntityConverter countryEntityConverter;

  @Bean
  public ConverterMapping countryEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(countryEntityConverter);
    mapping.setTypeCode(Country.class.getSimpleName());

    return mapping;
  }

}
