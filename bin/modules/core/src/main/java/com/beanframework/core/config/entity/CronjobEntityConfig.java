package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.CronjobEntityConverter;
import com.beanframework.cronjob.domain.Cronjob;

@Configuration
public class CronjobEntityConfig {

	@Bean
	public CronjobEntityConverter entityCronjobConverter() {
		return new CronjobEntityConverter();
	}

	@Bean
	public ConverterMapping entityCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCronjobConverter());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}
}
