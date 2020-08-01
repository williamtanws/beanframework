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
import com.beanframework.core.data.MediaDto;
import com.beanframework.media.domain.Media;

public class DtoMediaConverter extends AbstractDtoConverter<Media, MediaDto> implements DtoConverter<Media, MediaDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMediaConverter.class);

	@Override
	public MediaDto convert(Media source, DtoConverterContext context) throws ConverterException {
		try {
			MediaDto target = new MediaDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<MediaDto> convert(List<Media> sources, DtoConverterContext context) throws ConverterException {
		List<MediaDto> convertedList = new ArrayList<MediaDto>();
		try {
			for (Media source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}
}
