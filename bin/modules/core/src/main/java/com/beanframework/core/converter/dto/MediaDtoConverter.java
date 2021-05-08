package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.MediaDto;
import com.beanframework.media.domain.Media;

public class MediaDtoConverter extends AbstractDtoConverter<Media, MediaDto> implements DtoConverter<Media, MediaDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MediaDtoConverter.class);

	@Override
	public MediaDto convert(Media source) throws ConverterException {
		return super.convert(source, new MediaDto());
	}
}
