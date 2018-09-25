package com.beanframework.media.converter;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;

@Component
public class EntityMediaConverter implements Converter<Media, Media> {

	@Autowired
	private MediaService mediaService;

	@Override
	public Media convert(Media source) {

		Optional<Media> prototype = null;
		if (source.getUuid() != null) {
			prototype = mediaService.findEntityByUuid(source.getUuid());
			if (prototype.isPresent() == false) {
				prototype = Optional.of(mediaService.create());
			}
		}
		else {
			prototype = Optional.of(mediaService.create());
		}

		return convert(source, prototype.get());
	}

	private Media convert(Media source, Media prototype) {

		prototype.setId(source.getId());
		prototype.setName(source.getName());
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
