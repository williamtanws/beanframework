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
import com.beanframework.email.domain.EmailEnum.Result;
import com.beanframework.email.domain.EmailEnum.Status;

@Component
@DisallowConcurrentExecution
public class EmailJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(EmailJob.class);

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
			
			List<Email> oldEmails = modelService.findEntityByPropertiesAndSorts(oldProperties, oldSorts, null, null ,true, Email.class);
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
				
				List<Email> pendingEmails = modelService.findEntityByPropertiesAndSorts(pendingProperties, pendingSorts, null, null,true , Email.class);

				// Change pending email to processing email
				for (int i = 0; i < pendingEmails.size(); i++) {
					pendingEmails.get(i).setStatus(Status.PROCESSING);
					modelService.saveEntity(pendingEmails.get(i), Email.class);
				}
				modelService.flush();
			}

			// Get all processing email
			Map<String, Object> processingProperties = new HashMap<String, Object>();
			processingProperties.put(Email.STATUS, Status.PROCESSING);
			
			Map<String, Sort.Direction> processingSorts = new HashMap<String, Sort.Direction>();
			processingSorts.put(Email.CREATED_DATE, Sort.Direction.ASC);
			
			List<Email> processingEmails = modelService.findEntityByPropertiesAndSorts(processingProperties, processingSorts, null, EMAIL_PROCESS_NUMBER, true, Email.class);

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

						if (StringUtils.isNotBlank(email.getToRecipients())) {
							toRecipients = email.getToRecipients().split(";");
						}
						if (StringUtils.isNotBlank(email.getCcRecipients())) {
							ccRecipients = email.getCcRecipients().split(";");
						}
						if (StringUtils.isNotBlank(email.getBccRecipients())) {
							bccRecipients = email.getBccRecipients().split(";");
						}
						
						String workingDir = System.getProperty("user.dir");
						
						File attachmentFolder = new File(workingDir, EMAIL_ATTACHMENT_LOCATION + File.separator+email.getUuid());
						files = attachmentFolder.listFiles();
						
						sendEmail(toRecipients, ccRecipients, bccRecipients, email.getSubject(), email.getText(), email.getHtml(), files);
						
						email.setStatus(Status.SENT);
						email.setResult(Result.SUCCESS);
						email.setMessage(null);
						modelService.saveEntity(email, Email.class);
						
						sentEmail++;
					} catch (Exception e) {
						email.setStatus(Status.FAILED);
						email.setResult(Result.ERROR);
						email.setMessage(e.toString());
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
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

	private void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String text, String html,
			File[] files) throws Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
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
		if(StringUtils.isNotBlank(subject)) {
			helper.setSubject(subject);
		}
		if(StringUtils.isNotBlank(text) && StringUtils.isNotBlank(html)) {
			helper.setText(text, html);
		}
		else {
			if(StringUtils.isNotBlank(text)){
				helper.setText(text);
			}
			if(StringUtils.isNotBlank(html)){
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
