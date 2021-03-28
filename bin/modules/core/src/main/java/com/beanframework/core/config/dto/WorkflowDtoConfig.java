package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoWorkflowConverter;
import com.beanframework.core.converter.populator.WorkflowPopulator;
import com.beanframework.core.data.WorkflowDto;

@Configuration
public class WorkflowDtoConfig {

	@Bean
	public WorkflowPopulator workflowPopulator() {
		return new WorkflowPopulator();
	}

	@Bean
	public DtoWorkflowConverter dtoWorkflowConverter() {
		DtoWorkflowConverter converter = new DtoWorkflowConverter();
		converter.addPopulator(workflowPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoWorkflowConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoWorkflowConverter());
		mapping.setTypeCode(WorkflowDto.class.getSimpleName());

		return mapping;
	}
}
