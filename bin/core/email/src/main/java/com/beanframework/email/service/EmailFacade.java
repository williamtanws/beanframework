package com.beanframework.email.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.email.domain.Email;

public interface EmailFacade {
	
	void saveAttachment(Email email, MultipartFile[] attachments) throws IOException;
	
	void deleteAttachment(UUID uuid, String filename) throws IOException;
 
	void delete(UUID uuid) throws IOException;

	void deleteAll();

	Page<Email> page(Email email, int page, int size, Direction direction, String... properties);
}
