package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;

@Component
public class AuditorHistoryPopulator extends AbstractPopulator<Auditor, AuditorDto> implements Populator<Auditor, AuditorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(AuditorHistoryPopulator.class);

	@Override
	public void populate(Auditor source, AuditorDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
	}

}
