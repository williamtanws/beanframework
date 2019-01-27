package com.beanframework.email.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.EmailEnum.Result;
import com.beanframework.email.domain.EmailEnum.Status;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = EmailConstants.Table.EMAIL)
public class Email extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6066375988761984365L;
	public static final String NAME = "name";
	public static final String TO_RECIPIENTS = "toRecipients";
	public static final String CC_RECIPIENTS = "ccRecipients";
	public static final String BCC_RECIPIENTS = "bccRecipients";
	public static final String SUBJECT = "subject";
	public static final String TEXT = "text";
	public static final String HTML = "html";
	public static final String STATUS = "status";
	public static final String RESULT = "result";
	public static final String MESSAGE = "message";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	private String toRecipients;

	@Audited(withModifiedFlag = true)
	private String ccRecipients;

	@Audited(withModifiedFlag = true)
	private String bccRecipients;

	@Audited(withModifiedFlag = true)
	private String subject;

	@Audited(withModifiedFlag = true)
	private String text;

	@Audited(withModifiedFlag = true)
	@Lob
	@Column(length = 100000)
	private String html;

	@NotAudited
	@Enumerated(EnumType.STRING)
	private Status status;

	@NotAudited
	@Enumerated(EnumType.STRING)
	private Result result;

	@Audited(withModifiedFlag = true)
	@Lob
	@Column(length = 100000)
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
