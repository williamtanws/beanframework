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

		Email prototype;
		try {
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Email.UUID, source.getUuid());
				Email exists = modelService.findOneEntityByProperties(properties, Email.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Email.class);
				}
			} else {
				prototype = modelService.create(Email.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private Email convert(Email source, Email prototype) {

		prototype.setLastModifiedDate(new Date());

		if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
			prototype.setId(StringUtils.strip(source.getId()));

		if (StringUtils.equals(source.getToRecipients(), prototype.getToRecipients()) == false)
			prototype.setToRecipients(StringUtils.strip(source.getToRecipients()));

		if (StringUtils.equals(source.getCcRecipients(), prototype.getCcRecipients()) == false)
			prototype.setCcRecipients(StringUtils.strip(source.getCcRecipients()));

		if (StringUtils.equals(source.getBccRecipients(), prototype.getBccRecipients()) == false)
			prototype.setBccRecipients(StringUtils.strip(source.getBccRecipients()));

		if (StringUtils.equals(source.getSubject(), prototype.getSubject()) == false)
			prototype.setSubject(StringUtils.strip(source.getSubject()));

		if (StringUtils.equals(source.getText(), prototype.getText()) == false)
			prototype.setText(StringUtils.strip(source.getText()));

		if (StringUtils.equals(source.getHtml(), prototype.getHtml()) == false)
			prototype.setHtml(StringUtils.strip(source.getHtml()));

		if (source.getStatus() != prototype.getStatus()) {
			prototype.setStatus(source.getStatus());
		}

		return prototype;
	}

}
