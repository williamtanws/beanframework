package com.beanframework.email.job;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum.Status;

@Component
@DisallowConcurrentExecution
public class EmailJob implements Job {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailJob.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.from.name}")
	private String fromName;

	@Value("${spring.mail.from.email}")
	private String fromEmail;

	@Value("${module.email.attachment.location}")
	private String EMAIL_ATTACHMENT_LOCATION;
	
	@Autowired
	private ModelService modelService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		int EMAIL_PROCESS_NUMBER = 10;
		
		String limit = (String) context.getMergedJobDataMap().get("limit");
		if(limit != null) {
			EMAIL_PROCESS_NUMBER = Integer.valueOf(limit);
		}
		
		try {
			// Count old processing email
			Map<String, Object> oldProperties = new HashMap<String, Object>();
			oldProperties.put(Email.STATUS, Status.PROCESSING);
			
			Map<String, Sort.Direction> oldSorts = new HashMap<String, Sort.Direction>();
			oldSorts.put(Email.CREATED_DATE, Sort.Direction.ASC);
			
			List<Email> oldEmails = modelService.findEntityByPropertiesAndSorts(oldProperties, oldSorts, Email.class);
			int count = oldEmails.size();

			int numberOfPendingEmails = 0;

			if (count < EMAIL_PROCESS_NUMBER) {
				numberOfPendingEmails = EMAIL_PROCESS_NUMBER - count;
			}

			// Change number of pending email deducted by old email to processing email
			if (numberOfPendingEmails > 0) {
				
				Map<String, Object> pendingProperties = new HashMap<String, Object>();
				pendingProperties.put(Email.STATUS, Status.PENDING);
				
				Map<String, Sort.Direction> pendingSorts = new HashMap<String, Sort.Direction>();
				pendingSorts.put(Email.CREATED_DATE, Sort.Direction.ASC);
				
				List<Email> pendingEmails = modelService.findEntityByPropertiesAndSorts(pendingProperties, pendingSorts, Email.class);

				// Change pending email to processing email
				for (int i = 0; i < pendingEmails.size(); i++) {
					pendingEmails.get(i).setStatus(Status.PROCESSING);
					modelService.saveEntity(pendingEmails.get(i), Email.class);
				}
				modelService.saveAll();
			}

			// Get all processing email
			Map<String, Object> processingProperties = new HashMap<String, Object>();
			processingProperties.put(Email.STATUS, Status.PROCESSING);
			
			Map<String, Sort.Direction> processingSorts = new HashMap<String, Sort.Direction>();
			processingSorts.put(Email.CREATED_DATE, Sort.Direction.ASC);
			
			List<Email> processingEmails = modelService.findEntityByPropertiesAndSorts(processingProperties, processingSorts, EMAIL_PROCESS_NUMBER, Email.class);

			int sentEmail = 0;
			int failedEmail = 0;
			String result = null;

			if (processingEmails.size() > 0) {

				// Change processing email to sent email
				for (Email email : processingEmails) {

					try {
						String[] toRecipients = null;
						String[] ccRecipients = null;
						String[] bccRecipients = null;
						File[] files = null;

						if (StringUtils.isNotEmpty(email.getToRecipients())) {
							toRecipients = email.getToRecipients().split(";");
						}
						if (StringUtils.isNotEmpty(email.getCcRecipients())) {
							ccRecipients = email.getCcRecipients().split(";");
						}
						if (StringUtils.isNotEmpty(email.getBccRecipients())) {
							bccRecipients = email.getBccRecipients().split(";");
						}
						File attachmentFolder = new File(EMAIL_ATTACHMENT_LOCATION+File.separator+email.getUuid());
						files = attachmentFolder.listFiles();
						
						sendEmail(toRecipients, ccRecipients, bccRecipients, email.getSubject(), email.getText(), email.getHtml(), files);
						
						email.setStatus(Status.SENT);
						modelService.saveEntity(email, Email.class);
						
						sentEmail++;
					} catch (Exception e) {
						email.setStatus(Status.FAILED);
						email.setException(e.toString());
						modelService.saveEntity(email, Email.class);
						
						e.printStackTrace();
						LOGGER.error(e.toString(), e);
						
						failedEmail++;
					}
				}
			}

			result = new MessageFormat("{0} sent, {1} failed").format(new Object[] { sentEmail, failedEmail });
			context.setResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

//	private List<Email> findEmailByStatus(Status status, int maxResults) {
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		EntityTransaction entityTransaction = entityManager.getTransaction();
//		try {
//			entityTransaction.begin();
//
//			TypedQuery<Email> query = entityManager.createQuery(
//					"select e from Email e where e.status = :status order by e.createdDate ASC", Email.class);
//			query.setParameter("status", status);
//			if (maxResults > 0) {
//				query.setMaxResults(maxResults);
//			}
//
//			List<Email> result = query.getResultList();
//
//			entityTransaction.commit();
//			return result;
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//			entityTransaction.rollback();
//		} finally {
//			entityManager.close();
//		}
//
//		return null;
//	}
//
//	private void updateEmail(UUID uuid, Status status, String exception) {
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		EntityTransaction entityTransaction = entityManager.getTransaction();
//		try {
//			entityTransaction.begin();
//
//			Query query = entityManager.createQuery("Update Email set status = :status, exception = :exception where uuid = :uuid");
//			query.setParameter("status", status);
//			query.setParameter("uuid", uuid);
//			if(status.equals(Status.FAILED)) {
//				query.setParameter("exception", exception);
//			}
//			else {
//				query.setParameter("exception", null);
//			}
//
//			query.executeUpdate();
//
//			entityTransaction.commit();
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//			entityTransaction.rollback();
//		} finally {
//			entityManager.close();
//		}
//	}

	private void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String text, String html,
			File[] files) throws Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(fromEmail);
		if(to != null) {
			helper.setTo(to);
		}
		if(cc != null) {
			helper.setCc(cc);
		}
		if(bcc != null) {
			helper.setBcc(bcc);
		}
		if(StringUtils.isNotEmpty(subject)) {
			helper.setSubject(subject);
		}
		if(StringUtils.isNotEmpty(text) && StringUtils.isNotEmpty(html)) {
			helper.setText(text, html);
		}
		else {
			if(StringUtils.isNotEmpty(text)){
				helper.setText(text);
			}
			if(StringUtils.isNotEmpty(html)){
				helper.setText(html, true);
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
