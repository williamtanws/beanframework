package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.SiteEntityConverter;

@Configuration
public class SiteEntityConfig {

	@Bean
	public SiteEntityConverter entitySiteConverter() {
		return new SiteEntityConverter();
	}

	@Bean
	public ConverterMapping entitySiteConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entitySiteConverter());
		mapping.setTypeCode(Site.class.getSimpleName());

		return mapping;
	}
}
