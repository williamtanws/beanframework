package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.LanguageEntityConverter;
import com.beanframework.internationalization.domain.Language;

@Configuration
public class LanguageEntityConfig {

	@Autowired
	public LanguageEntityConverter languageEntityConverter;

	@Bean
	public ConverterMapping languageEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(languageEntityConverter);
		mapping.setTypeCode(Language.class.getSimpleName());

		return mapping;
	}
}
