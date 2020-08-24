package com.beanframework.core.converter.populator;

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
public class RegionBasicPopulator extends AbstractPopulator<Region, RegionDto> implements Populator<Region, RegionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(RegionBasicPopulator.class);

	@Autowired
	private CountryBasicPopulator countryBasicPopulator;
	
	@Override
	public void populate(Region source, RegionDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setActive(source.getActive());
			
			if (source.getCountry() != null) {
				Country entity = modelService.findOneByUuid(source.getCountry(), Country.class);
				target.setCountry(modelService.getDto(entity, CountryDto.class, new DtoConverterContext(countryBasicPopulator)));
			}
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
