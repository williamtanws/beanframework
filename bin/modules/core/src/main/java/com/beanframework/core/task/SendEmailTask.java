package com.beanframework.core.task;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.job.EmailJob;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum.Result;
import com.beanframework.email.domain.EmailEnum.Status;
import com.beanframework.media.service.MediaService;

@Component("sendEmailTask")
public class SendEmailTask implements JavaDelegate {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SendEmailTask.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.from.name}")
	private String fromName;

	@Value("${spring.mail.from.email}")
	private String fromEmail;

	@Autowired
	private ModelService modelService;

	@Autowired
	private MediaService mediaService;

	public void execute(DelegateExecution execution) {
		@SuppressWarnings("unchecked")
		List<Email> processingEmails = (List<Email>) execution.getVariable(ProcessEmailTask.PROCESSING_EMAILS);

		int sentEmail = 0;
		int failedEmail = 0;
		
		// Change processing email to sent email
		for (Email email : processingEmails) {

			try {
				String[] toRecipients = null;
				String[] ccRecipients = null;
				String[] bccRecipients = null;

				if (StringUtils.isNotBlank(email.getToRecipients())) {
					toRecipients = email.getToRecipients().split(";");
				}
				if (StringUtils.isNotBlank(email.getCcRecipients())) {
					ccRecipients = email.getCcRecipients().split(";");
				}
				if (StringUtils.isNotBlank(email.getBccRecipients())) {
					bccRecipients = email.getBccRecipients().split(";");
				}

				List<File> files = mediaService.getFiles(email.getMedias().stream().toArray(UUID[]::new));

				sendEmail(toRecipients, ccRecipients, bccRecipients, email.getSubject(), email.getText(), email.getHtml(), files.stream().toArray(File[]::new));

				email.setStatus(Status.SENT);
				email.setResult(Result.SUCCESS);
				email.setMessage(null);

				sentEmail++;
			} catch (Exception e) {
				email.setStatus(Status.FAILED);
				email.setResult(Result.ERROR);
				email.setMessage(e.toString());

				e.printStackTrace();
				LOGGER.error(e.toString(), e);

				failedEmail++;
			} finally {

				try {
					modelService.saveEntity(email);
				} catch (BusinessException e) {
					LOGGER.error(e.toString(), e);
				}
			}
		}
		
		execution.setVariable(EmailJob.SENT_EMAIL, sentEmail);
		execution.setVariable(EmailJob.FAILED_EMAIL, failedEmail);
	}

	private void sendEmail(String[] to, String[] cc, String[] bcc, String subject, String text, String html, File[] files) throws Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
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