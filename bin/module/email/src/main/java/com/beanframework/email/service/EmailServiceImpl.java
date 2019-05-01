package com.beanframework.email.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;
import com.beanframework.email.specification.EmailSpecification;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private ModelService modelService;

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.from.email}")
	private String fromEmail;

	@Override
	public Email findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Email.class);
	}

	@Override
	public Email findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Email.class);
	}

	@Override
	public List<Email> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Email.class);
	}

	@Override
	public Email saveEntity(Email model) throws BusinessException {
		return (Email) modelService.saveEntity(model, Email.class);
	}

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
	public Page<Email> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(EmailSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Email.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Email.class);
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

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Email.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Email.class);
	}

	@Override
	public void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String text, String html, File[] files) throws Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setFrom(fromEmail);
		if (to != null) {
			helper.setTo(to);
		}
		if (cc != null) {
			helper.setCc(cc);
		}
		if (bcc != null) {
			helper.setBcc(bcc);
		}
		if (StringUtils.isNotBlank(subject)) {
			helper.setSubject(subject);
		}
		if (StringUtils.isNotBlank(text) && StringUtils.isNotBlank(html)) {
			helper.setText(text, html);
		} else {
			if (StringUtils.isNotBlank(text)) {
				helper.setText(text);
			}
			if (StringUtils.isNotBlank(html)) {
				helper.setText(html);
			}
		}

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				FileSystemResource file = new FileSystemResource(files[i]);
				helper.addAttachment(files[i].getName(), file);
			}
		}
		javaMailSender.send(mimeMessage);
	}
}
