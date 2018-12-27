package com.beanframework.email.domain;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.EmailEnum.Status;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = EmailConstants.Table.EMAIL)
public class Email extends GenericDomain {

	private static final long serialVersionUID = 2324052965289006873L;

	public static final String TORECIPIENTS = "toRecipients";
	public static final String CCRECIPIENTS = "ccRecipients";
	public static final String BCCRECIPIENTS = "bccRecipients";
	public static final String SUBJECT = "subject";
	public static final String TEXT = "text";
	public static final String HTML = "html";
	public static final String ATTACHMENTS = "attachments";
	public static final String STATUS = "status";

	private String toRecipients;

	private String ccRecipients;

	private String bccRecipients;

	private String subject;

	private String text;

	@Lob
	@Column(length = 100000)
	private String html;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String exception;

	@Transient
	private File[] attachments;

	public String getToRecipients() {
		return toRecipients;
	}

	public void setToRecipients(String toRecipients) {
		this.toRecipients = toRecipients;
	}

	public String getCcRecipients() {
		return ccRecipients;
	}

	public void setCcRecipients(String ccRecipients) {
		this.ccRecipients = ccRecipients;
	}

	public String getBccRecipients() {
		return bccRecipients;
	}

	public void setBccRecipients(String bccRecipients) {
		this.bccRecipients = bccRecipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public File[] getAttachments() {
		return attachments;
	}

	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}

}
