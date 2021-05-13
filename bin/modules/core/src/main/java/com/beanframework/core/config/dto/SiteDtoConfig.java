package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.SiteDtoConverter;
import com.beanframework.core.converter.populator.SitePopulator;
import com.beanframework.core.data.SiteDto;

@Configuration
public class SiteDtoConfig {

  @Autowired
  public SitePopulator sitePopulator;

  @Bean
  public SiteDtoConverter siteDtoConverter() {
    SiteDtoConverter converter = new SiteDtoConverter();
    converter.addPopulator(sitePopulator);
    return converter;
  }

  @Bean
  public ConverterMapping siteDtoConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(siteDtoConverter());
    mapping.setTypeCode(SiteDto.class.getSimpleName());

    return mapping;
  }
}
