package com.beanframework.email.service;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.email.domain.Email;

@Component
public class EmailFacadeImpl implements EmailFacade {
	
	Logger logger = LoggerFactory.getLogger(EmailFacadeImpl.class);

	@Autowired
	private EmailService emailService;
	
	@Override
	public void saveAttachment(Email email, MultipartFile[] attachments) throws BusinessException {
		try {
			emailService.saveAttachment(email, attachments);
		} catch (IOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public void deleteAttachment(UUID uuid, String filename) throws BusinessException {
		try {
			emailService.deleteAttachment(uuid, filename);
		} catch (IOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
