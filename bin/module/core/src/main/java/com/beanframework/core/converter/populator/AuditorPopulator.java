package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.PopulatorException;

public class AuditorPopulator extends AbstractPopulator<Auditor, AuditorDto> implements Populator<Auditor, AuditorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(AuditorPopulator.class);

	@Override
	public void populate(Auditor source, AuditorDto target) throws PopulatorException {
		populateGeneric(source, target);
		target.setName(source.getName());
	}

}
