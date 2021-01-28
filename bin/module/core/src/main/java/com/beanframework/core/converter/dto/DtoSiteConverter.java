package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.SiteDto;

public class DtoSiteConverter extends AbstractDtoConverter<Site, SiteDto> implements DtoConverter<Site, SiteDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoSiteConverter.class);

	@Override
	public SiteDto convert(Site source) throws ConverterException {
		return super.convert(source, new SiteDto());
	}

}
