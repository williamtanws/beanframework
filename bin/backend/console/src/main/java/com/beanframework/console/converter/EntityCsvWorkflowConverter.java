package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.WorkflowCsv;
import com.beanframework.workflow.domain.Workflow;

@Component
public class EntityCsvWorkflowConverter implements EntityCsvConverter<WorkflowCsv, Workflow> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvWorkflowConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Workflow convert(WorkflowCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Workflow.ID, source.getId());
				Workflow prototype = modelService.findOneByProperties(properties, Workflow.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Workflow.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Workflow convertToEntity(WorkflowCsv source, Workflow prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());
			
			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

			if (StringUtils.isNotBlank(source.getClasspath()))
				prototype.setClasspath(source.getClasspath());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
