package com.beanframework.email.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.email.domain.Email;

public interface EmailService {

	void saveAttachment(Email email, MultipartFile[] attachments) throws IOException;

	void deleteAttachment(UUID uuid, String filename) throws IOException;

	void delete(UUID uuid) throws IOException;

	void deleteAll() throws IOException;
}
