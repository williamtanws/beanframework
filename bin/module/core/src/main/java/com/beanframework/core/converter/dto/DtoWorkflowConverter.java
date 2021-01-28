package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.workflow.domain.Workflow;

public class DtoWorkflowConverter extends AbstractDtoConverter<Workflow, WorkflowDto> implements DtoConverter<Workflow, WorkflowDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoWorkflowConverter.class);

	@Override
	public WorkflowDto convert(Workflow source) throws ConverterException {
		return super.convert(source, new WorkflowDto());
	}

}
