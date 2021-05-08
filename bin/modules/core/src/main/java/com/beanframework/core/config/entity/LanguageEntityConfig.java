package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.LanguageEntityConverter;
import com.beanframework.internationalization.domain.Language;

@Configuration
public class LanguageEntityConfig {

	@Bean
	public LanguageEntityConverter entityLanguageConverter() {
		return new LanguageEntityConverter();
	}

	@Bean
	public ConverterMapping entityLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityLanguageConverter());
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}
}
