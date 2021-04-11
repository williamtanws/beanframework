package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.workflow.domain.Workflow;


public class WorkflowPopulator extends AbstractPopulator<Workflow, WorkflowDto> implements Populator<Workflow, WorkflowDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(WorkflowPopulator.class);

	@Override
	public void populate(Workflow source, WorkflowDto target) throws PopulatorException {
		populateGeneric(source, target);
		target.setName(source.getName());
		target.setDeploymentId(source.getDeploymentId());
		target.setClasspath(source.getClasspath());
		target.setActive(source.getActive());
	}

}
