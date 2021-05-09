package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.SiteEntityConverter;

@Configuration
public class SiteEntityConfig {

	@Autowired
	public SiteEntityConverter siteEntityConverter;

	@Bean
	public ConverterMapping siteEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(siteEntityConverter);
		mapping.setTypeCode(Site.class.getSimpleName());

		return mapping;
	}
}
