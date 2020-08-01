package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.workflow.domain.Workflow;

public class DtoWorkflowConverter extends AbstractDtoConverter<Workflow, WorkflowDto> implements DtoConverter<Workflow, WorkflowDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoWorkflowConverter.class);

	@Override
	public WorkflowDto convert(Workflow source, DtoConverterContext context) throws ConverterException {
		try {
			WorkflowDto target = new WorkflowDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<WorkflowDto> convert(List<Workflow> sources, DtoConverterContext context) throws ConverterException {
		List<WorkflowDto> convertedList = new ArrayList<WorkflowDto>();
		try {
			for (Workflow source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
