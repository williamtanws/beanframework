package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.LanguageDtoConverter;
import com.beanframework.core.converter.populator.LanguagePopulator;
import com.beanframework.core.data.LanguageDto;

@Configuration
public class LanguageDtoConfig {

	@Autowired
	public LanguagePopulator languagePopulator;

	@Bean
	public LanguageDtoConverter languageDtoConverter() {
		LanguageDtoConverter converter = new LanguageDtoConverter();
		converter.addPopulator(languagePopulator);
		return converter;
	}

	@Bean
	public ConverterMapping languageDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(languageDtoConverter());
		mapping.setTypeCode(LanguageDto.class.getSimpleName());

		return mapping;
	}
}
