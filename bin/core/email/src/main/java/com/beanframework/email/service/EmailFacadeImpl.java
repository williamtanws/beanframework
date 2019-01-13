package com.beanframework.email.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.email.domain.Email;

@Component
public class EmailFacadeImpl implements EmailFacade {

	Logger logger = LoggerFactory.getLogger(EmailFacadeImpl.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private ModelService modelService;

	@Override
	public void saveAttachment(Email email, MultipartFile[] attachments) throws BusinessException {
		try {
			emailService.saveAttachment(email, attachments);
		} catch (IOException e) {
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
	public Page<Email> findPage(Specification<Email> specification, PageRequest pageable) throws Exception {
		return modelService.findDtoPage(specification, pageable, Email.class);
	}

	@Override
	public Email findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, Email.class);
	}

	@Override
	public Email createDto(Email model) throws BusinessException {
		return (Email) modelService.saveDto(model, Email.class);
	}

	@Override
	public Email updateDto(Email model) throws BusinessException {
		return (Email) modelService.saveDto(model, Email.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, Email.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Email.class);

		return revisions;
	}

	@Override
	public Email findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return emailService.findOneDtoByProperties(properties);
	}
}
