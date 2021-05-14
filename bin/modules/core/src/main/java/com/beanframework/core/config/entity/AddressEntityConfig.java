package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.AddressEntityConverter;
import com.beanframework.user.domain.Address;

@Configuration
public class AddressEntityConfig {

  @Autowired
  public AddressEntityConverter addressEntityConverter;

  @Bean
  public ConverterMapping addressEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(addressEntityConverter);
    mapping.setTypeCode(Address.class.getSimpleName());

    return mapping;
  }

}
