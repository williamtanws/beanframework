package com.beanframework.email.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.email.domain.Email;

public class EntityEmailConverter implements EntityConverter<Email, Email> {

	@Autowired
	private ModelService modelService;

	@Override
	public Email convert(Email source) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Email.UUID, source.getUuid());
				Email prototype = modelService.findOneEntityByProperties(properties, Email.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Email.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

	}

	private Email convert(Email source, Email prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false) {
			prototype.setId(StringUtils.strip(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getName(), prototype.getName()) == false) {
			prototype.setName(StringUtils.strip(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getToRecipients(), prototype.getToRecipients()) == false) {
			prototype.setToRecipients(StringUtils.strip(source.getToRecipients()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getCcRecipients(), prototype.getCcRecipients()) == false) {
			prototype.setCcRecipients(StringUtils.strip(source.getCcRecipients()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getBccRecipients(), prototype.getBccRecipients()) == false) {
			prototype.setBccRecipients(StringUtils.strip(source.getBccRecipients()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getSubject(), prototype.getSubject()) == false) {
			prototype.setSubject(StringUtils.strip(source.getSubject()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getText(), prototype.getText()) == false) {
			prototype.setText(StringUtils.strip(source.getText()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(source.getHtml(), prototype.getHtml()) == false) {
			prototype.setHtml(StringUtils.strip(source.getHtml()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getStatus() != prototype.getStatus()) {
			prototype.setStatus(source.getStatus());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
