package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.workflow.domain.Workflow;

public class EntityWorkflowConverter implements EntityConverter<WorkflowDto, Workflow> {

	@Autowired
	private ModelService modelService;

	@Override
	public Workflow convert(WorkflowDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Workflow.UUID, source.getUuid());
				Workflow prototype = modelService.findByProperties(properties, Workflow.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Workflow.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Workflow convertToEntity(WorkflowDto source, Workflow prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getClasspath()), prototype.getClasspath()) == Boolean.FALSE) {
			prototype.setClasspath(StringUtils.stripToNull(source.getClasspath()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
