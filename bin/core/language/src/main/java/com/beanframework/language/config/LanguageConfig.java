package com.beanframework.language.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.language.converter.EntityLanguageConverter;
import com.beanframework.language.domain.Language;

@Configuration
public class LanguageConfig {
	
	@Bean
	public DtoLanguageConverter dtoLanguageConverter() {
		return new DtoLanguageConverter();
	}

	@Bean
	public ConverterMapping dtoLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoLanguageConverter());
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public EntityLanguageConverter entityLanguageConverter() {
		return new EntityLanguageConverter();
	}

	@Bean
	public ConverterMapping entityLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityLanguageConverter());
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}
}
