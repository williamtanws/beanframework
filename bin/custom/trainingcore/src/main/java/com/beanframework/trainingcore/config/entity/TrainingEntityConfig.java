package com.beanframework.trainingcore.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.training.domain.Training;
import com.beanframework.trainingcore.converter.entity.EntityTrainingConverter;

@Configuration
public class TrainingEntityConfig {

	@Bean
	public EntityTrainingConverter entityTrainingConverter() {
		return new EntityTrainingConverter();
	}

	@Bean
	public ConverterMapping entityTrainingConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityTrainingConverter());
		mapping.setTypeCode(Training.class.getSimpleName());

		return mapping;
	}
}
