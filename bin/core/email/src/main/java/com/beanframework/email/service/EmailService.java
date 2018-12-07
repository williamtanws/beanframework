package com.beanframework.email.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.email.domain.Email;

public interface EmailService {

	Email create();

	Email initDefaults(Email email);

	Email save(Email email);

	void saveAttachment(Email email, MultipartFile[] attachments) throws IOException;

	void deleteAttachment(UUID uuid, String filename) throws IOException;

	void delete(UUID uuid) throws IOException;

	void deleteAll() throws IOException;

	Optional<Email> findEntityByUuid(UUID uuid);

	Email findByUuid(UUID uuid);

	Page<Email> page(Email email, Pageable pageable);

}
