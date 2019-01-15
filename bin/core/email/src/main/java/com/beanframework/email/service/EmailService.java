package com.beanframework.email.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.email.domain.Email;

public interface EmailService {

	void saveAttachment(Email email, MultipartFile[] attachments) throws IOException;

	void deleteAttachment(UUID uuid, String filename) throws IOException;

	void delete(UUID uuid) throws IOException, BusinessException;

	void deleteAll() throws IOException, BusinessException;

	Email create() throws Exception;

	Email findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	Email saveEntity(Email model) throws BusinessException;
}
