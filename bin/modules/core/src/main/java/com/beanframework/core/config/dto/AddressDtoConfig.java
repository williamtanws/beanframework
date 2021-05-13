package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.AddressDtoConverter;
import com.beanframework.core.converter.populator.AddressPopulator;
import com.beanframework.core.data.AddressDto;

@Configuration
public class AddressDtoConfig {

  @Autowired
  public AddressPopulator addressPopulator;

  @Bean
  public AddressDtoConverter addressDtoConverter() {
    AddressDtoConverter converter = new AddressDtoConverter();
    converter.addPopulator(addressPopulator);
    return converter;
  }

  @Bean
  public ConverterMapping addressDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(addressDtoConverter());
    mapping.setTypeCode(AddressDto.class.getSimpleName());

    return mapping;
  }
}
