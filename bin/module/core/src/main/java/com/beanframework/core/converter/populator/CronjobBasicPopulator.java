package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;

@Component
public class CronjobBasicPopulator extends AbstractPopulator<Cronjob, CronjobDto> implements Populator<Cronjob, CronjobDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CronjobBasicPopulator.class);

	@Override
	public void populate(Cronjob source, CronjobDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setJobGroup(source.getJobGroup());
			target.setName(source.getName());
			target.setStatus(source.getStatus());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
