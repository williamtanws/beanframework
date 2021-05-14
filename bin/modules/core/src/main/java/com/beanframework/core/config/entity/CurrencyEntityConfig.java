package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.CurrencyEntityConverter;
import com.beanframework.internationalization.domain.Currency;

@Configuration
public class CurrencyEntityConfig {

  @Autowired
  public CurrencyEntityConverter currencyEntityConverter;

  @Bean
  public ConverterMapping currencyEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(currencyEntityConverter);
    mapping.setTypeCode(Currency.class.getSimpleName());

    return mapping;
  }

}
