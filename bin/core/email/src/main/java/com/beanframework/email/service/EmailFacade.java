package com.beanframework.email.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.email.domain.Email;

public interface EmailFacade {
	
	void saveAttachment(Email email, MultipartFile[] attachments) throws IOException;
	
	void deleteAttachment(UUID uuid, String filename) throws IOException;
 
}
