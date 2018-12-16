package com.beanframework.email.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.service.ModelService;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailSpecification;

@Component
public class EmailFacadeImpl implements EmailFacade {
	
	Logger logger = LoggerFactory.getLogger(EmailFacadeImpl.class);
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private EmailService emailService;
	
	@Override
	public void saveAttachment(Email email, MultipartFile[] attachments) throws IOException {
		emailService.saveAttachment(email, attachments);
	}
	
	@Override
	public void deleteAttachment(UUID uuid, String filename) throws IOException {
		emailService.deleteAttachment(uuid, filename);
	}

	@Override
	public void delete(UUID uuid) throws IOException {
		emailService.delete(uuid);
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
	public Page<Email> page(Email Email, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		Page<Email> EmailPage = modelService.findPage(EmailSpecification.findByCriteria(Email), pageRequest,
				Email.class);

		List<Email> content = modelService.getDto(EmailPage.getContent());
		return new PageImpl<Email>(content, EmailPage.getPageable(), EmailPage.getTotalElements());
	}
}
