package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.cronjob.domain.CronjobData;

@Component
public class CronjobDataHistoryPopulator extends AbstractPopulator<CronjobData, CronjobDataDto> implements Populator<CronjobData, CronjobDataDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CronjobDataHistoryPopulator.class);

	@Override
	public void populate(CronjobData source, CronjobDataDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
		target.setValue(source.getValue());
	}

}
