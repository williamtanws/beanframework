package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.MediaDtoConverter;
import com.beanframework.core.converter.populator.MediaPopulator;
import com.beanframework.core.data.MediaDto;

@Configuration
public class MediaDtoConfig {

  @Autowired
  public MediaPopulator mediaPopulator;

  @Bean
  public MediaDtoConverter mediaDtoConverter() {
    MediaDtoConverter converter = new MediaDtoConverter();
    converter.addPopulator(mediaPopulator);
    return converter;
  }

  @Bean
  public ConverterMapping mediaDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(mediaDtoConverter());
    mapping.setTypeCode(MediaDto.class.getSimpleName());

    return mapping;
  }
}
