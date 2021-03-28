package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobPopulator extends AbstractPopulator<Cronjob, CronjobDto> implements Populator<Cronjob, CronjobDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CronjobPopulator.class);

	@Override
	public void populate(Cronjob source, CronjobDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setJobClass(source.getJobClass());
			target.setJobGroup(source.getJobGroup());
			target.setName(source.getName());
			target.setDescription(source.getDescription());
			target.setCronExpression(source.getCronExpression());
			target.setStartup(source.getStartup());
			target.setStatus(source.getStatus());
			target.setResult(source.getResult());
			target.setMessage(source.getMessage());
			target.setJobTrigger(source.getJobTrigger());
			target.setTriggerStartDate(source.getTriggerStartDate());
			target.setLastTriggeredDate(source.getLastTriggeredDate());
			target.setLastStartExecutedDate(source.getLastStartExecutedDate());
			target.setLastFinishExecutedDate(source.getLastFinishExecutedDate());
			target.setParameters(source.getParameters());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
