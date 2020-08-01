package com.beanframework.core.converter.populator;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;

@Component
public class EmailFullPopulator extends AbstractPopulator<Email, EmailDto> implements Populator<Email, EmailDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EmailFullPopulator.class);

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public void populate(Email source, EmailDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
		target.setToRecipients(source.getToRecipients());
		target.setCcRecipients(source.getCcRecipients());
		target.setBccRecipients(source.getBccRecipients());
		target.setSubject(source.getSubject());
		target.setText(source.getText());
		target.setHtml(source.getHtml());
		target.setStatus(source.getStatus());
		target.setResult(source.getResult());
		target.setMessage(source.getMessage());

		String workingDir = System.getProperty("user.dir");

		File emailAttachmentFolder = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator + target.getUuid());
		target.setAttachments(emailAttachmentFolder.listFiles());
	}

}
