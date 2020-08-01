package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.enumuration.domain.Enumeration;

@Component
public class EnumerationFullPopulator extends AbstractPopulator<Enumeration, EnumerationDto> implements Populator<Enumeration, EnumerationDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EnumerationFullPopulator.class);

	@Override
	public void populate(Enumeration source, EnumerationDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
		target.setSort(source.getSort());
	}

}
