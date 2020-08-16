package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CountryDto;
import com.beanframework.internationalization.domain.Country;

@Component
public class CountryBasicPopulator extends AbstractPopulator<Country, CountryDto> implements Populator<Country, CountryDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CountryBasicPopulator.class);

	@Override
	public void populate(Country source, CountryDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
		target.setActive(source.getActive());
	}

}
