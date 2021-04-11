package com.beanframework.core.converter.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.workflow.domain.Workflow;

public class EntityWorkflowConverter implements EntityConverter<WorkflowDto, Workflow> {

	@Autowired
	private ModelService modelService;

	@Override
	public Workflow convert(WorkflowDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Workflow prototype = modelService.findOneByUuid(source.getUuid(), Workflow.class);

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

		if (StringUtils.equals(StringUtils.stripToNull(source.getDeploymentId()), prototype.getDeploymentId()) == Boolean.FALSE) {
			prototype.setDeploymentId(StringUtils.stripToNull(source.getDeploymentId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getClasspath()), prototype.getClasspath()) == Boolean.FALSE) {
			prototype.setClasspath(StringUtils.stripToNull(source.getClasspath()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}
		
		if (prototype.getActive() == source.getActive() == Boolean.FALSE) {
			prototype.setActive(source.getActive());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
