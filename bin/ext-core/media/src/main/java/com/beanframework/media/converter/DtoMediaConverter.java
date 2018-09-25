package com.beanframework.media.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;

@Component
public class DtoMediaConverter implements Converter<Media, Media> {

	@Autowired
	private MediaService mediaService;

	@Override
	public Media convert(Media source) {
		return convert(source, mediaService.create());
	}

	public List<Media> convert(List<Media> sources) {
		List<Media> convertedList = new ArrayList<Media>();
		for (Media source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Media convert(Media source, Media prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setName(source.getName());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		return prototype;
	}

}
