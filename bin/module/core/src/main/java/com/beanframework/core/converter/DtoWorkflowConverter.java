package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.workflow.domain.Workflow;

public class DtoWorkflowConverter extends AbstractDtoConverter<Workflow, WorkflowDto> implements DtoConverter<Workflow, WorkflowDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoWorkflowConverter.class);

	@Override
	public WorkflowDto convert(Workflow source, DtoConverterContext context) throws ConverterException {
		return convert(source, new WorkflowDto(), context);
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

	private WorkflowDto convert(Workflow source, WorkflowDto prototype, DtoConverterContext context) throws ConverterException {
		try {
			convertCommonProperties(source, prototype, context);

			prototype.setName(source.getName());
			prototype.setClasspath(source.getClasspath());
			prototype.setDeploymentId(source.getDeploymentId());

			if (ConvertRelationType.ALL == context.getConverModelType()) {
				convertAll(source, prototype, context);
			} else if (ConvertRelationType.BASIC == context.getConverModelType()) {
				convertRelation(source, prototype, context);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

	private void convertAll(Workflow source, WorkflowDto prototype, DtoConverterContext context) throws Exception {

	}

	private void convertRelation(Workflow source, WorkflowDto prototype, DtoConverterContext context) throws Exception {
	}

}
