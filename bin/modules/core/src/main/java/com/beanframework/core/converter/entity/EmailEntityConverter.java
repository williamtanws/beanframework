package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;
import com.beanframework.media.domain.Media;

@Component
public class EmailEntityConverter implements EntityConverter<EmailDto, Email> {

	@Value(EmailConstants.EMAIL_MEDIA_LOCATION)
	private String EMAIL_MEDIA_LOCATION;

	@Autowired
	private ModelService modelService;

	@Override
	public Email convert(EmailDto source) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Email prototype = modelService.findOneByUuid(source.getUuid(), Email.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Email.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private Email convertToEntity(EmailDto source, Email prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getToRecipients()), prototype.getToRecipients()) == Boolean.FALSE) {
				prototype.setToRecipients(StringUtils.stripToNull(source.getToRecipients()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getCcRecipients()), prototype.getCcRecipients()) == Boolean.FALSE) {
				prototype.setCcRecipients(StringUtils.stripToNull(source.getCcRecipients()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getBccRecipients()), prototype.getBccRecipients()) == Boolean.FALSE) {
				prototype.setBccRecipients(StringUtils.stripToNull(source.getBccRecipients()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getSubject()), prototype.getSubject()) == Boolean.FALSE) {
				prototype.setSubject(StringUtils.stripToNull(source.getSubject()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getText()), prototype.getText()) == Boolean.FALSE) {
				prototype.setText(StringUtils.stripToNull(source.getText()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getHtml()), prototype.getHtml()) == Boolean.FALSE) {
				prototype.setHtml(StringUtils.stripToNull(source.getHtml()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getStatus() != prototype.getStatus()) {
				prototype.setStatus(source.getStatus());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Medias
			if (source.getSelectedMediaUuids() != null) {

				Iterator<UUID> MediasIterator = prototype.getMedias().iterator();
				while (MediasIterator.hasNext()) {
					UUID enumeration = MediasIterator.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedMediaUuids().length; i++) {
						if (enumeration.equals(UUID.fromString(source.getSelectedMediaUuids()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						MediasIterator.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedMediaUuids().length; i++) {

					boolean add = true;
					MediasIterator = prototype.getMedias().iterator();
					while (MediasIterator.hasNext()) {
						UUID enumeration = MediasIterator.next();

						if (enumeration.equals(UUID.fromString(source.getSelectedMediaUuids()[i]))) {
							add = false;
						}
					}

					if (add) {
						Media enumeration = modelService.findOneByUuid(UUID.fromString(source.getSelectedMediaUuids()[i]), Media.class);
						if (enumeration != null) {
							prototype.getMedias().add(enumeration.getUuid());
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			} else if (prototype.getMedias() != null && prototype.getMedias().isEmpty() == false) {
				for (final Iterator<UUID> itr = prototype.getMedias().iterator(); itr.hasNext();) {
					itr.next();
					itr.remove();
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
