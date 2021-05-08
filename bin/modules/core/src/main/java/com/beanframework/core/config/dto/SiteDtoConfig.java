package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.SiteDtoConverter;
import com.beanframework.core.converter.populator.SitePopulator;
import com.beanframework.core.data.SiteDto;

@Configuration
public class SiteDtoConfig {

	@Bean
	public SitePopulator sitePopulator() {
		return new SitePopulator();
	}

	@Bean
	public SiteDtoConverter dtoSiteConverter() {
		SiteDtoConverter converter = new SiteDtoConverter();
		converter.addPopulator(sitePopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoSiteConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoSiteConverter());
		mapping.setTypeCode(SiteDto.class.getSimpleName());

		return mapping;
	}
}
