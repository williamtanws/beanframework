package com.beanframework.email.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.email.domain.Email;

public interface EmailFacade {
	
	void saveAttachment(Email email, MultipartFile[] attachments) throws BusinessException;
	
	void deleteAttachment(UUID uuid, String filename) throws BusinessException;
 
}
