package com.beanframework.backoffice.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoSiteConverter;
import com.beanframework.core.data.SiteDto;

@Configuration
public class SiteDtoConfig {

	@Bean
	public DtoSiteConverter dtoSiteConverter() {
		return new DtoSiteConverter();
	}

	@Bean
	public ConverterMapping DtoSiteConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoSiteConverter());
		mapping.setTypeCode(SiteDto.class.getSimpleName());

		return mapping;
	}
}
