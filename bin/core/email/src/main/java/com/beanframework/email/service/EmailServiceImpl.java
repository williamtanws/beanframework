package com.beanframework.email.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.service.ModelService;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;

@Service
public class EmailServiceImpl implements EmailService {

	Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public void saveAttachment(Email email, MultipartFile[] attachments) throws IOException {
		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + email.getUuid());
		FileUtils.forceMkdir(emailAttachmentFolder);

		for (int i = 0; i < attachments.length; i++) {
			if (attachments[i].isEmpty() == false) {
				File attachment = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + email.getUuid() + File.separator + attachments[i].getOriginalFilename());
				attachments[i].transferTo(attachment);
			}
		}
	}

	@Override
	public void deleteAttachment(UUID uuid, String filename) throws IOException {
		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + uuid);
		File[] files = emailAttachmentFolder.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(filename)) {
				FileUtils.forceDelete(files[i]);
			}
		}
	}

	@Transactional
	@Override
	public void delete(UUID uuid) throws IOException {
		modelService.remove(uuid, Email.class);

		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + uuid);
		FileUtils.deleteDirectory(emailAttachmentFolder);
	}

	@Transactional
	@Override
	public void deleteAll() throws IOException {
		modelService.removeAll(Email.class);

		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION);
		FileUtils.deleteDirectory(emailAttachmentFolder);
	}
}
