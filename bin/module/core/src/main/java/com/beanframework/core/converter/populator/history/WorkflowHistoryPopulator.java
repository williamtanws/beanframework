package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.workflow.domain.Workflow;

@Component
public class WorkflowHistoryPopulator extends AbstractPopulator<Workflow, WorkflowDto> implements Populator<Workflow, WorkflowDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(WorkflowHistoryPopulator.class);

	@Override
	public void populate(Workflow source, WorkflowDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
		target.setClasspath(source.getClasspath());
		target.setDeploymentId(source.getDeploymentId());
	}

}
