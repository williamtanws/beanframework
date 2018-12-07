package com.beanframework.email.job;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum.Status;

@Component
@DisallowConcurrentExecution
public class EmailJob implements Job {

	Logger logger = LoggerFactory.getLogger(EmailJob.class);

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Value("${module.email.process.number:100}")
	private int EMAIL_PROCESS_NUMBER;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.from.name}")
	private String fromName;

	@Value("${spring.mail.from.email}")
	private String fromEmail;

	@Value("${module.email.attachment.location}")
	private String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Count old processing email
		int count = findEmailByStatus(Status.PROCESSING, 0).size();

		int numberOfPendingEmails = 0;

		if (count < EMAIL_PROCESS_NUMBER) {
			numberOfPendingEmails = EMAIL_PROCESS_NUMBER - count;
		}

		if (numberOfPendingEmails > 0) {

			// Change pending email to processing email
			List<Email> pendingEmails = findEmailByStatus(Status.PENDING, numberOfPendingEmails);

			for (Email email : pendingEmails) {
				updateEmail(email.getUuid(), Status.PROCESSING, null);
			}
		}

		// Get all processing email
		List<Email> processingEmails = findEmailByStatus(Status.PROCESSING, EMAIL_PROCESS_NUMBER);

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
					
					updateEmail(email.getUuid(), Status.SENT, null);
					
					sentEmail++;
				} catch (Exception e) {
					updateEmail(email.getUuid(), Status.FAILED, e.toString());
					logger.error(e.toString(), e);
					failedEmail++;
				}
			}
		}

		result = new MessageFormat("{0} sent, {1} failed").format(new Object[] { sentEmail, failedEmail });
		context.setResult(result);
	}

	private List<Email> findEmailByStatus(Status status, int maxResults) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();

			TypedQuery<Email> query = entityManager.createQuery(
					"select e from Email e where e.status = :status order by e.createdDate ASC", Email.class);
			query.setParameter("status", status);
			if (maxResults > 0) {
				query.setMaxResults(maxResults);
			}

			List<Email> result = query.getResultList();

			entityTransaction.commit();
			return result;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}

		return null;
	}

	private void updateEmail(UUID uuid, Status status, String exception) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();

			Query query = entityManager.createQuery("Update Email set status = :status, exception = :exception where uuid = :uuid");
			query.setParameter("status", status);
			query.setParameter("uuid", uuid);
			if(status.equals(Status.FAILED)) {
				query.setParameter("exception", exception);
			}
			else {
				query.setParameter("exception", null);
			}

			query.executeUpdate();

			entityTransaction.commit();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			entityTransaction.rollback();
		} finally {
			entityManager.close();
		}
	}

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
