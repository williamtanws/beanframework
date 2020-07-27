package com.beanframework.core.converter.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;

public class DtoEmailConverter extends AbstractDtoConverter<Email, EmailDto> implements DtoConverter<Email, EmailDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoEmailConverter.class);

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public EmailDto convert(Email source, DtoConverterContext context) throws ConverterException {
		return convert(source, new EmailDto(), context);
	}

	public List<EmailDto> convert(List<Email> sources, DtoConverterContext context) throws ConverterException {
		List<EmailDto> convertedList = new ArrayList<EmailDto>();
		for (Email source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	public EmailDto convert(Email source, EmailDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

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

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
