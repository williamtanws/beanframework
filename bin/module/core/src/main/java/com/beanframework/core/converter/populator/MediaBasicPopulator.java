package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.MediaDto;
import com.beanframework.media.domain.Media;

@Component
public class MediaBasicPopulator extends AbstractPopulator<Media, MediaDto> implements Populator<Media, MediaDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MediaBasicPopulator.class);

	@Override
	public void populate(Media source, MediaDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setFileName(source.getFileName());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
