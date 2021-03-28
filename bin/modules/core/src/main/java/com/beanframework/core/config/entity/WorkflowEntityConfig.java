package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityWorkflowConverter;
import com.beanframework.workflow.domain.Workflow;

@Configuration
public class WorkflowEntityConfig {

	@Bean
	public EntityWorkflowConverter entityWorkflowConverter() {
		return new EntityWorkflowConverter();
	}

	@Bean
	public ConverterMapping entityWorkflowConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityWorkflowConverter());
		mapping.setTypeCode(Workflow.class.getSimpleName());

		return mapping;
	}
}
