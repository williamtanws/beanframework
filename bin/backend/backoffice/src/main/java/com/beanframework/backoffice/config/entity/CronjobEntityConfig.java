package com.beanframework.backoffice.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntityCronjobConverter;
import com.beanframework.core.converter.EntityCronjobDataConverter;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;

@Configuration
public class CronjobEntityConfig {

	@Bean
	public EntityCronjobConverter entityCronjobConverter() {
		return new EntityCronjobConverter();
	}

	@Bean
	public ConverterMapping entityCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCronjobConverter());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCronjobDataConverter entityCronjobDataConverter() {
		return new EntityCronjobDataConverter();
	}

	@Bean
	public ConverterMapping entityCronjobDataConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCronjobDataConverter());
		mapping.setTypeCode(CronjobData.class.getSimpleName());

		return mapping;
	}
}
