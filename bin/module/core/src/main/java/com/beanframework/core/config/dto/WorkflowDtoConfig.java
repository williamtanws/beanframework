package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoWorkflowConverter;
import com.beanframework.core.data.WorkflowDto;

@Configuration
public class WorkflowDtoConfig {

	@Bean
	public DtoWorkflowConverter dtoWorkflowConverter() {
		return new DtoWorkflowConverter();
	}

	@Bean
	public ConverterMapping dtoWorkflowConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoWorkflowConverter());
		mapping.setTypeCode(WorkflowDto.class.getSimpleName());

		return mapping;
	}
}
