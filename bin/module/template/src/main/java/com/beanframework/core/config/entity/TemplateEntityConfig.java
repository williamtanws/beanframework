package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityTemplateConverter;
import com.beanframework.template.domain.Template;

@Configuration
public class TemplateEntityConfig {

	@Bean
	public EntityTemplateConverter entityTemplateConverter() {
		return new EntityTemplateConverter();
	}

	@Bean
	public ConverterMapping entityTemplateConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityTemplateConverter());
		mapping.setTypeCode(Template.class.getSimpleName());

		return mapping;
	}
}
