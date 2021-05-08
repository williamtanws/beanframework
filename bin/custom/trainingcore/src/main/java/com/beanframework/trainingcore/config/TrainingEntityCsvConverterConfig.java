package com.beanframework.trainingcore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.trainingcore.converter.entity.csv.EntityCsvTrainingConverter;
import com.beanframework.trainingcore.csv.TrainingCsv;

@Configuration
public class TrainingEntityCsvConverterConfig {

	@Bean
	public EntityCsvTrainingConverter entityCsvTrainingConverter() {
		return new EntityCsvTrainingConverter();
	}

	@Bean
	public ConverterMapping entityCsvTrainingConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvTrainingConverter());
		mapping.setTypeCode(TrainingCsv.class.getSimpleName());

		return mapping;
	}
}
