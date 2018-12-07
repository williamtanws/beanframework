package com.beanframework.email.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.email.EmailConstants;
import com.beanframework.email.converter.DtoEmailConverter;
import com.beanframework.email.converter.EntityEmailConverter;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum;
import com.beanframework.email.domain.EmailSpecification;
import com.beanframework.email.repository.EmailRepository;

@Service
public class EmailServiceImpl implements EmailService {

	Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private EntityEmailConverter entityEmailConverter;

	@Autowired
	private DtoEmailConverter dtoEmailConverter;

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public Email create() {
		return initDefaults(new Email());
	}

	@Override
	public Email initDefaults(Email email) {
		email.setStatus(EmailEnum.Status.DRAFT);
		return email;
	}

	@Transactional(readOnly = false)
	@Override
	public Email save(Email email) {

		email = entityEmailConverter.convert(email);
		email = emailRepository.save(email);
		email = dtoEmailConverter.convert(email);

		return email;
	}

	@Override
	public void saveAttachment(Email email, MultipartFile[] attachments) throws IOException {
		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + email.getUuid());
		FileUtils.forceMkdir(emailAttachmentFolder);

		for (int i = 0; i < attachments.length; i++) {
			if (attachments[i].isEmpty() == false) {
				File attachment = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + email.getUuid() + File.separator + attachments[i].getOriginalFilename());
				attachments[i].transferTo(attachment);
			}
		}
	}

	@Override
	public void deleteAttachment(UUID uuid, String filename) throws IOException {
		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + uuid);
		File[] files = emailAttachmentFolder.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(filename)) {
				FileUtils.forceDelete(files[i]);
			}
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) throws IOException {
		emailRepository.deleteById(uuid);

		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION + File.separator + uuid);
		FileUtils.deleteDirectory(emailAttachmentFolder);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteAll() throws IOException {
		emailRepository.deleteAll();

		File emailAttachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION);
		FileUtils.deleteDirectory(emailAttachmentFolder);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Email> findEntityByUuid(UUID uuid) {
		return emailRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Email findByUuid(UUID uuid) {
		Optional<Email> email = emailRepository.findByUuid(uuid);

		if (email.isPresent()) {
			return dtoEmailConverter.convert(email.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Email> page(Email email, Pageable pageable) {
		Page<Email> page = emailRepository.findAll(EmailSpecification.findByCriteria(email), pageable);
		List<Email> content = dtoEmailConverter.convert(page.getContent());
		return new PageImpl<Email>(content, page.getPageable(), page.getTotalElements());
	}

	public EmailRepository getEmailRepository() {
		return emailRepository;
	}

	public void setEmailRepository(EmailRepository emailRepository) {
		this.emailRepository = emailRepository;
	}

	public EntityEmailConverter getEntityEmailConverter() {
		return entityEmailConverter;
	}

	public void setEntityEmailConverter(EntityEmailConverter entityEmailConverter) {
		this.entityEmailConverter = entityEmailConverter;
	}

	public DtoEmailConverter getDtoEmailConverter() {
		return dtoEmailConverter;
	}

	public void setDtoEmailConverter(DtoEmailConverter dtoEmailConverter) {
		this.dtoEmailConverter = dtoEmailConverter;
	}
}
