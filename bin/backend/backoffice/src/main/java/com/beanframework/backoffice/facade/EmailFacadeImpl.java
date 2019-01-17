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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.backoffice.data.EmailDto;
import com.beanframework.backoffice.data.EmailSearch;
import com.beanframework.backoffice.data.EmailSpecification;
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
	public Page<EmailDto> findPage(EmailSearch search, PageRequest pageable) throws Exception {
		Page<Email> page = emailService.findEntityPage(search.toString(), EmailSpecification.findByCriteria(search), pageable);
		List<EmailDto> dtos = modelService.getDto(page.getContent(), EmailDto.class);
		return new PageImpl<EmailDto>(dtos, page.getPageable(), page.getTotalElements());
	}

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
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = emailService.findHistoryByUuid(uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], EmailDto.class);
//		}

		return revisions;
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
}
