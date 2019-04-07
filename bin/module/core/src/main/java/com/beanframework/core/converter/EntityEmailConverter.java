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
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.domain.Email;
import com.beanframework.email.service.EmailService;

public class EntityEmailConverter implements EntityConverter<EmailDto, Email> {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public Email convert(EmailDto source, EntityConverterContext context) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Email.UUID, source.getUuid());
				Email prototype = emailService.findOneEntityByProperties(properties);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Email.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private Email convertToEntity(EmailDto source, Email prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getToRecipients()), prototype.getToRecipients()) == false) {
			prototype.setToRecipients(StringUtils.stripToNull(source.getToRecipients()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getCcRecipients()), prototype.getCcRecipients()) == false) {
			prototype.setCcRecipients(StringUtils.stripToNull(source.getCcRecipients()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getBccRecipients()), prototype.getBccRecipients()) == false) {
			prototype.setBccRecipients(StringUtils.stripToNull(source.getBccRecipients()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getSubject()), prototype.getSubject()) == false) {
			prototype.setSubject(StringUtils.stripToNull(source.getSubject()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getText()), prototype.getText()) == false) {
			prototype.setText(StringUtils.stripToNull(source.getText()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getHtml()), prototype.getHtml()) == false) {
			prototype.setHtml(StringUtils.stripToNull(source.getHtml()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getStatus() != prototype.getStatus()) {
			prototype.setStatus(source.getStatus());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
