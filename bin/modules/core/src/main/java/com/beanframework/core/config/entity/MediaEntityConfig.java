package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.MediaEntityConverter;
import com.beanframework.media.domain.Media;

@Configuration
public class MediaEntityConfig {

  @Autowired
  public MediaEntityConverter mediaEntityConverter;

  @Bean
  public ConverterMapping mediaEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(mediaEntityConverter);
    mapping.setTypeCode(Media.class.getSimpleName());

    return mapping;
  }
}
