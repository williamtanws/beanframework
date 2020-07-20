package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.SiteDto;

public class DtoSiteConverter extends AbstractDtoConverter<Site, SiteDto> implements DtoConverter<Site, SiteDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoSiteConverter.class);

	@Override
	public SiteDto convert(Site source, DtoConverterContext context) throws ConverterException {
		return convert(source, new SiteDto(), context);
	}

	public List<SiteDto> convert(List<Site> sources, DtoConverterContext context) throws ConverterException {
		List<SiteDto> convertedList = new ArrayList<SiteDto>();
		try {
			for (Site source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private SiteDto convert(Site source, SiteDto prototype, DtoConverterContext context) throws ConverterException {
		try {
			convertCommonProperties(source, prototype, context);

			if (ConvertRelationType.ALL == context.getConverModelType()) {
				convertAll(source, prototype, context);
			} else if (ConvertRelationType.BASIC == context.getConverModelType()) {
				convertBasic(source, prototype, context);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

	private void convertAll(Site source, SiteDto prototype, DtoConverterContext context) throws Exception {
		prototype.setName(source.getName());
		prototype.setUrl(source.getUrl());

	}

	private void convertBasic(Site source, SiteDto prototype, DtoConverterContext context) throws Exception {
		prototype.setName(source.getName());
		prototype.setUrl(source.getUrl());

	}

}