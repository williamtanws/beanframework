package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MediaDto;
import com.beanframework.media.domain.Media;
import com.beanframework.user.domain.User;

public class EntityMediaConverter implements EntityConverter<MediaDto, Media> {

	@Autowired
	private ModelService modelService;

	@Override
	public Media convert(MediaDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Media.UUID, source.getUuid());
				Media prototype = modelService.findOneEntityByProperties(properties, true, Media.class);

				if (prototype != null) {
					return convertDto(source, prototype);
				}
			}

			return convertDto(source, modelService.create(Media.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Media convertDto(MediaDto source, Media prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getFileName()), prototype.getFileName()) == false) {
				prototype.setFileName(StringUtils.stripToNull(source.getFileName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getFileType()), prototype.getFileType()) == false) {
				prototype.setFileType(StringUtils.stripToNull(source.getFileType()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getFileSize() == null) {
				if (prototype.getFileSize() != null) {
					prototype.setFileSize(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getFileSize() == null || prototype.getFileSize().equals(source.getFileSize()) == false) {
					prototype.setFileSize(source.getFileSize());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getTitle()), prototype.getTitle()) == false) {
				prototype.setTitle(StringUtils.stripToNull(source.getTitle()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getCaption()), prototype.getCaption()) == false) {
				prototype.setCaption(StringUtils.stripToNull(source.getCaption()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getAltText()), prototype.getAltText()) == false) {
				prototype.setAltText(StringUtils.stripToNull(source.getAltText()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getDescription()), prototype.getDescription()) == false) {
				prototype.setDescription(StringUtils.stripToNull(source.getDescription()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getUrl()), prototype.getUrl()) == false) {
				prototype.setUrl(StringUtils.stripToNull(source.getUrl()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getLocation()), prototype.getLocation()) == false) {
				prototype.setLocation(StringUtils.stripToNull(source.getLocation()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getUser() != null && source.getUser() == null) {
				prototype.setUser(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else if (prototype.getUser().getUuid().equals(source.getUser().getUuid()) == false) {
				User entityUser = modelService.findOneEntityByUuid(source.getUser().getUuid(), false, User.class);

				if (entityUser != null) {
					prototype.setUser(entityUser);
					prototype.setLastModifiedDate(lastModifiedDate);
				} else {
					throw new ConverterException("User UUID not found: " + source.getUser().getUuid());
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
