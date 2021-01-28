package com.beanframework.core.facade;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.domain.Email;
import com.beanframework.email.service.EmailService;
import com.beanframework.email.specification.EmailSpecification;

@Component
public class EmailFacadeImpl  extends AbstractFacade<Email, EmailDto> implements EmailFacade {
	
	private static final Class<Email> entityClass = Email.class;
	private static final Class<EmailDto> dtoClass = EmailDto.class;

	@Autowired
	private EmailService emailService;
	
	@Override
	public EmailDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public EmailDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public EmailDto create(EmailDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public EmailDto update(EmailDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<EmailDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, EmailSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public EmailDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

	@Override
	public void saveAttachment(EmailDto dto, MultipartFile[] attachments) throws BusinessException {
		try {
			emailService.saveAttachment(modelService.getEntity(dto, entityClass), attachments);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteAttachment(UUID uuid, String filename) throws BusinessException {
		try {
			emailService.deleteAttachment(uuid, filename);
		} catch (IOException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
