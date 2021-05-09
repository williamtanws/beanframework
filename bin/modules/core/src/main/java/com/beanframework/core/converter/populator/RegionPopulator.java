package com.beanframework.core.converter.populator;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;

@Component
public class RegionPopulator extends AbstractPopulator<Region, RegionDto> implements Populator<Region, RegionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(RegionPopulator.class);

	@Override
	public void populate(Region source, RegionDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			target.setActive(source.getActive());
			target.setCountry(populateCountry(source.getCountry()));

		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public CountryDto populateCountry(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			Country source = modelService.findOneByUuid(uuid, Country.class);
			if (source == null) {
				return null;
			}

			CountryDto target = new CountryDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setName(source.getName());
			target.setActive(source.getActive());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
