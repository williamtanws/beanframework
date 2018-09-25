package com.beanframework.email.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.email.domain.Email;

public interface EmailFacade {

	Email create();

	Email initDefaults(Email email);

	Email save(Email email, Errors bindingResult);
	
	void saveAttachment(Email email, MultipartFile[] attachments, Errors bindingResult);
	
	void deleteAttachment(UUID uuid, String filename, Errors bindingResult);
 
	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	Email findByUuid(UUID uuid);

	Page<Email> page(Email email, int page, int size, Direction direction, String... properties);
}
