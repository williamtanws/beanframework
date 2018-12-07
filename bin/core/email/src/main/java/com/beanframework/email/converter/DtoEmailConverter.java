package com.beanframework.email.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;
import com.beanframework.email.service.EmailService;

@Component
public class DtoEmailConverter implements Converter<Email, Email> {

	@Autowired
	private EmailService emailService;
	
	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public Email convert(Email source) {
		return convert(source, emailService.create());
	}

	public List<Email> convert(List<Email> sources) {
		List<Email> convertedList = new ArrayList<Email>();
		for (Email source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Email convert(Email source, Email prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setToRecipients(source.getToRecipients());
		prototype.setCcRecipients(source.getCcRecipients());
		prototype.setBccRecipients(source.getBccRecipients());
		prototype.setSubject(source.getSubject());
		prototype.setAttachments(source.getAttachments());
		prototype.setText(source.getText());
		prototype.setHtml(source.getHtml());
		prototype.setStatus(source.getStatus());
		prototype.setException(source.getException());
		
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		
		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + prototype.getUuid());
		prototype.setAttachments(emailAttachmentFolder.listFiles());

		return prototype;
	}

}
