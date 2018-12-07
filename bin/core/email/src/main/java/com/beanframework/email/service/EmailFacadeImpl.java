package com.beanframework.email.service;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.email.domain.Email;
import com.beanframework.email.validator.DeleteEmailValidator;
import com.beanframework.email.validator.SaveEmailValidator;

@Component
public class EmailFacadeImpl implements EmailFacade {
	
	Logger logger = LoggerFactory.getLogger(EmailFacadeImpl.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private SaveEmailValidator saveEmailValidator;
	
	@Autowired
	private DeleteEmailValidator deleteEmailValidator;

	@Override
	public Email create() {
		return emailService.create();
	}

	@Override
	public Email initDefaults(Email email) {
		return emailService.initDefaults(email);
	}

	@Override
	public Email save(Email email, Errors bindingResult) {
		saveEmailValidator.validate(email, bindingResult);

		if (bindingResult.hasErrors()) {
			return email;
		}

		return emailService.save(email);
	}
	
	@Override
	public void saveAttachment(Email email, MultipartFile[] attachments, Errors bindingResult) {
		try {
			emailService.saveAttachment(email, attachments);
		} catch (IOException e) {
			bindingResult.reject("email", e.toString());
		}
	}
	
	@Override
	public void deleteAttachment(UUID uuid, String filename, Errors bindingResult) {
		try {
			emailService.deleteAttachment(uuid, filename);
		} catch (IOException e) {
			bindingResult.reject("email", e.toString());
		}
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteEmailValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			try {
				emailService.delete(uuid);
			} catch (IOException e) {
				bindingResult.reject("email", e.toString());
			}
		}
	}

	@Override
	public void deleteAll() {
		try {
			emailService.deleteAll();
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
	}

	@Override
	public Email findByUuid(UUID uuid) {
		return emailService.findByUuid(uuid);
	}

	@Override
	public Page<Email> page(Email email, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return emailService.page(email, pageRequest);
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public SaveEmailValidator getSaveEmailValidator() {
		return saveEmailValidator;
	}

	public void setSaveEmailValidator(SaveEmailValidator saveEmailValidator) {
		this.saveEmailValidator = saveEmailValidator;
	}



}
