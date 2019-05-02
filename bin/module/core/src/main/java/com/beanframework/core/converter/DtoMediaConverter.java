package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.utils.SizeUtils;
import com.beanframework.core.data.MediaDto;
import com.beanframework.media.domain.Media;

public class DtoMediaConverter extends AbstractDtoConverter<Media, MediaDto> implements DtoConverter<Media, MediaDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMediaConverter.class);

	@Override
	public MediaDto convert(Media source, DtoConverterContext context) throws ConverterException {
		return convert(source, new MediaDto(), context);
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

	private MediaDto convert(Media source, MediaDto prototype, DtoConverterContext context) throws ConverterException {
		try {
			convertCommonProperties(source, prototype, context);

			prototype.setFileName(source.getFileName());
			prototype.setFileType(source.getFileType());
			prototype.setFileSize(SizeUtils.humanReadableByteCount(source.getFileSize(), true));
			prototype.setTitle(source.getTitle());
			prototype.setCaption(source.getCaption());
			prototype.setAltText(source.getAltText());
			prototype.setDescription(source.getDescription());
			prototype.setUrl(source.getUrl());
			prototype.setFolder(source.getFolder());

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
