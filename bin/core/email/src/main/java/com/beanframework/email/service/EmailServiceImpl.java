package com.beanframework.email.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private ModelService modelService;

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public Email create() throws Exception {
		return modelService.create(Email.class);
	}

	@Cacheable(value = "EmailOne", key = "#uuid")
	@Override
	public Email findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Email.class);
	}

	@Cacheable(value = "EmailOneProperties", key = "#properties")
	@Override
	public Email findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Email.class);
	}

	@Cacheable(value = "EmailsSorts", key = "#sorts")
	@Override
	public List<Email> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Email.class);
	}

	@Cacheable(value = "EmailsPage", key = "'query:'+#query+',pageable'+#pageable")
	@Override
	public <T> Page<Email> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, Email.class);
	}

	@Cacheable(value = "EmailsHistory", key = "'uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, Email.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "EmailOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "EmailOneProperties", allEntries = true), //
			@CacheEvict(value = "EmailsSorts", allEntries = true), //
			@CacheEvict(value = "EmailsPage", allEntries = true), //
			@CacheEvict(value = "EmailsHistory", allEntries = true), //
			@CacheEvict(value = "EmailsRelatedHistory", allEntries = true) }) //
	@Override
	public Email saveEntity(Email model) throws BusinessException {
		return (Email) modelService.saveEntity(model, Email.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "EmailOne", key = "#uuid"), //
			@CacheEvict(value = "EmailOneProperties", allEntries = true), //
			@CacheEvict(value = "EmailsSorts", allEntries = true), //
			@CacheEvict(value = "EmailsPage", allEntries = true), //
			@CacheEvict(value = "EmailsHistory", allEntries = true), //
			@CacheEvict(value = "EmailsRelatedHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Email model = modelService.findOneEntityByUuid(uuid, Email.class);
			modelService.deleteByEntity(model, Email.class);

			String workingDir = System.getProperty("user.dir");

			File emailAttachmentFolder = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator + uuid);
			FileUtils.deleteDirectory(emailAttachmentFolder);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void saveAttachment(Email email, MultipartFile[] attachments) throws IOException {

		String workingDir = System.getProperty("user.dir");

		File emailAttachmentFolder = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator + email.getUuid());
		FileUtils.forceMkdir(emailAttachmentFolder);

		for (int i = 0; i < attachments.length; i++) {
			if (attachments[i].isEmpty() == false) {
				File attachment = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator + email.getUuid() + File.separator + attachments[i].getOriginalFilename());
				attachments[i].transferTo(attachment);
			}
		}
	}

	@Override
	public void deleteAttachment(UUID uuid, String filename) throws IOException {

		String workingDir = System.getProperty("user.dir");

		File emailAttachmentFolder = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator + uuid);
		File[] files = emailAttachmentFolder.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(filename)) {
				FileUtils.forceDelete(files[i]);
			}
		}
	}
}
