package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Region;

public class RegionDtoConverter extends AbstractDtoConverter<Region, RegionDto> implements DtoConverter<Region, RegionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(RegionDtoConverter.class);

	@Override
	public RegionDto convert(Region source) throws ConverterException {
		return super.convert(source, new RegionDto());
	}

}
