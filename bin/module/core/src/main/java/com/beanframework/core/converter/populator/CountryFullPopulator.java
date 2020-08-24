package com.beanframework.core.converter.populator;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;

@Component
public class CountryFullPopulator extends AbstractPopulator<Country, CountryDto> implements Populator<Country, CountryDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CountryFullPopulator.class);

	@Autowired
	private RegionBasicPopulator regionBasicPopulator;

	@Override
	public void populate(Country source, CountryDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setActive(source.getActive());

			if (source.getRegions() != null && source.getRegions().isEmpty() == false) {
				for (UUID uuid : source.getRegions()) {
					Region entity = modelService.findOneByUuid(uuid, Region.class);
					target.getRegions().add(modelService.getDto(entity, RegionDto.class, new DtoConverterContext(regionBasicPopulator)));
				}
			}
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}

	}

}
