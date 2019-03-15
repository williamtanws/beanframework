package com.beanframework.backoffice.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntitySiteConverter;

@Configuration
public class SiteEntityConfig {

	@Bean
	public EntitySiteConverter entitySiteConverter() {
		return new EntitySiteConverter();
	}

	@Bean
	public ConverterMapping entitySiteConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entitySiteConverter());
		mapping.setTypeCode(Site.class.getSimpleName());

		return mapping;
	}
}
