package com.beanframework.backoffice.facade;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.backoffice.data.EmailDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.email.domain.Email;
import com.beanframework.email.service.EmailService;

@Component
public class EmailFacadeImpl implements EmailFacade {

	Logger logger = LoggerFactory.getLogger(EmailFacadeImpl.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private ModelService modelService;

	@Override
	public EmailDto findOneByUuid(UUID uuid) throws Exception {
		Email entity = emailService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, EmailDto.class);
	}

	@Override
	public EmailDto findOneProperties(Map<String, Object> properties) throws Exception {
		Email entity = emailService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, EmailDto.class);
	}

	@Override
	public EmailDto create(EmailDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public EmailDto update(EmailDto model) throws BusinessException {
		return save(model);
	}

	public EmailDto save(EmailDto dto) throws BusinessException {
		try {
			Email entity = modelService.getEntity(dto, Email.class);
			entity = (Email) emailService.saveEntity(entity);

			return modelService.getDto(entity, EmailDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		emailService.deleteByUuid(uuid);
	}

	@Override
	public Page<EmailDto> findPage(DataTableRequest<EmailDto> dataTableRequest) throws Exception {
		Page<Email> page = emailService.findEntityPage(dataTableRequest);
		List<EmailDto> dtos = modelService.getDto(page.getContent(), EmailDto.class);
		return new PageImpl<EmailDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return emailService.count();
	}

	@Override
	public void saveAttachment(EmailDto dto, MultipartFile[] attachments) throws BusinessException {
		try {
			emailService.saveAttachment(modelService.getEntity(dto, Email.class), attachments);
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

	@Override
	public List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<Object[]> revisions = emailService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Email)
				entityObject[0] = modelService.getDto(entityObject[0], EmailDto.class);
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {
		return emailService.findCountHistory(dataTableRequest);
	}
}
