package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.common.utils.SizeUtils;
import com.beanframework.core.data.MediaDto;
import com.beanframework.media.domain.Media;


public class MediaPopulator extends AbstractPopulator<Media, MediaDto> implements Populator<Media, MediaDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MediaPopulator.class);

	@Override
	public void populate(Media source, MediaDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setFileName(source.getFileName());
			target.setFileType(source.getFileType());
			target.setFileSize(source.getFileSize() == null ? null : SizeUtils.humanReadableByteCount(source.getFileSize(), true));
			target.setTitle(source.getTitle());
			target.setCaption(source.getCaption());
			target.setAltText(source.getAltText());
			target.setDescription(source.getDescription());
			target.setUrl(source.getUrl());
			target.setFolder(source.getFolder());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
