package com.beanframework.email.service;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.email.domain.Email;

@Component
public class EmailFacadeImpl implements EmailFacade {
	
	Logger logger = LoggerFactory.getLogger(EmailFacadeImpl.class);

	@Autowired
	private EmailService emailService;
	
	@Override
	public void saveAttachment(Email email, MultipartFile[] attachments) throws IOException {
		emailService.saveAttachment(email, attachments);
	}
	
	@Override
	public void deleteAttachment(UUID uuid, String filename) throws IOException {
		emailService.deleteAttachment(uuid, filename);
	}
}
