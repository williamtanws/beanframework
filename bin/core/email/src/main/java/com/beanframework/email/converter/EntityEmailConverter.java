package com.beanframework.email.converter;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum.Status;
import com.beanframework.email.service.EmailService;

@Component
public class EntityEmailConverter implements EntityConverter<Email, Email> {

	@Autowired
	private EmailService emailService;

	@Override
	public Email convert(Email source) {

		Optional<Email> prototype = null;
		if (source.getUuid() != null) {
			prototype = emailService.findEntityByUuid(source.getUuid());
			if (prototype.isPresent() == false) {
				prototype = Optional.of(emailService.create());
			}
		}
		else {
			prototype = Optional.of(emailService.create());
		}

		return convert(source, prototype.get());
	}

	private Email convert(Email source, Email prototype) {

		prototype.setToRecipients(source.getToRecipients());
		prototype.setCcRecipients(source.getCcRecipients());
		prototype.setBccRecipients(source.getBccRecipients());
		prototype.setSubject(source.getSubject());
		prototype.setText(source.getText());
		prototype.setHtml(source.getHtml());
		if(source.getStatus() == null) {
			prototype.setStatus(Status.DRAFT);
		}
		else {
			prototype.setStatus(source.getStatus());
		}
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
