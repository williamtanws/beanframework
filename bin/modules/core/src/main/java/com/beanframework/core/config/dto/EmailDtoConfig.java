package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.EmailDtoConverter;
import com.beanframework.core.converter.populator.EmailPopulator;
import com.beanframework.core.data.EmailDto;

@Configuration
public class EmailDtoConfig {

  @Autowired
  public EmailPopulator emailPopulator;

  @Bean
  public EmailDtoConverter emailDtoConverter() {
    EmailDtoConverter converter = new EmailDtoConverter();
    converter.addPopulator(emailPopulator);
    return converter;
  }

  @Bean
  public ConverterMapping emailDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(emailDtoConverter());
    mapping.setTypeCode(EmailDto.class.getSimpleName());

    return mapping;
  }
}
