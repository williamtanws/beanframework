package com.beanframework.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.console.converter.EntityCsvConfigurationConverter;
import com.beanframework.console.csv.ConfigurationCsv;

@Configuration
public class ConsoleEntityCsvConfig {

	@Bean
	public EntityCsvConfigurationConverter entityCsvConfigurationConverter() {
		return new EntityCsvConfigurationConverter();
	}

	@Bean
	public ConverterMapping entityCsvConfigurationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvConfigurationConverter());
		mapping.setTypeCode(ConfigurationCsv.class.getSimpleName());

		return mapping;
	}
}
