package com.beanframework.email.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;

public class DtoEmailConverter implements DtoConverter<Email, Email> {
	
	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public Email convert(Email source) {
		return convert(source, new Email());
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
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		
		prototype.setName(source.getName());
		prototype.setToRecipients(source.getToRecipients());
		prototype.setCcRecipients(source.getCcRecipients());
		prototype.setBccRecipients(source.getBccRecipients());
		prototype.setSubject(source.getSubject());
		prototype.setText(source.getText());
		prototype.setHtml(source.getHtml());
		prototype.setStatus(source.getStatus());
		prototype.setResult(source.getResult());
		prototype.setMessage(source.getMessage());
		
		String workingDir = System.getProperty("user.dir");
		
		File emailAttachmentFolder = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator + prototype.getUuid());
		prototype.setAttachments(emailAttachmentFolder.listFiles());

		return prototype;
	}

}
