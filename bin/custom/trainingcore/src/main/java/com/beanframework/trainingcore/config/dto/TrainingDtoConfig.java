package com.beanframework.trainingcore.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.trainingcore.converter.dto.DtoTrainingConverter;
import com.beanframework.trainingcore.converter.populator.TrainingPopulator;
import com.beanframework.trainingcore.data.TrainingDto;

@Configuration
public class TrainingDtoConfig {

	@Bean
	public TrainingPopulator trainingPopulator() {
		return new TrainingPopulator();
	}

	@Bean
	public DtoTrainingConverter dtoTrainingConverter() {
		DtoTrainingConverter converter = new DtoTrainingConverter();
		converter.addPopulator(trainingPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoTrainingConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoTrainingConverter());
		mapping.setTypeCode(TrainingDto.class.getSimpleName());

		return mapping;
	}
}
