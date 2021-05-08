package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.LanguageDtoConverter;
import com.beanframework.core.converter.populator.LanguagePopulator;
import com.beanframework.core.data.LanguageDto;

@Configuration
public class LanguageDtoConfig {

	@Bean
	public LanguagePopulator languagePopulator() {
		return new LanguagePopulator();
	}

	@Bean
	public LanguageDtoConverter dtoLanguageConverter() {
		LanguageDtoConverter converter = new LanguageDtoConverter();
		converter.addPopulator(languagePopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoLanguageConverter());
		mapping.setTypeCode(LanguageDto.class.getSimpleName());

		return mapping;
	}
}
