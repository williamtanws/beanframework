package com.beanframework.email.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum.Status;

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
		
		if (source.getId() != null) {
			prototype.setId(source.getId());
		}
		prototype.setLastModifiedDate(new Date());

		prototype.setToRecipients(source.getToRecipients());
		prototype.setCcRecipients(source.getCcRecipients());
		prototype.setBccRecipients(source.getBccRecipients());
		prototype.setSubject(source.getSubject());
		prototype.setText(source.getText());
		prototype.setHtml(source.getHtml());
		if (source.getStatus() == null) {
			prototype.setStatus(Status.DRAFT);
		} else {
			prototype.setStatus(source.getStatus());
		}
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
