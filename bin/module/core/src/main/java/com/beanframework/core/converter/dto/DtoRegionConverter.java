package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Region;

public class DtoRegionConverter extends AbstractDtoConverter<Region, RegionDto> implements DtoConverter<Region, RegionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoRegionConverter.class);

	@Override
	public RegionDto convert(Region source, DtoConverterContext context) throws ConverterException {
		try {
			RegionDto target = new RegionDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<RegionDto> convert(List<Region> sources, DtoConverterContext context) throws ConverterException {
		List<RegionDto> convertedList = new ArrayList<RegionDto>();
		try {
			for (Region source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
