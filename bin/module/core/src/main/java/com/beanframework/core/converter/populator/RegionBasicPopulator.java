package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Region;

@Component
public class RegionBasicPopulator extends AbstractPopulator<Region, RegionDto> implements Populator<Region, RegionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(RegionBasicPopulator.class);

	@Override
	public void populate(Region source, RegionDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
		target.setActive(source.getActive());
	}

}
