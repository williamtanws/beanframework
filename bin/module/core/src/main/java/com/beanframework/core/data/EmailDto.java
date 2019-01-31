package com.beanframework.core.data;

import java.io.File;

import com.beanframework.common.data.GenericDto;
import com.beanframework.email.domain.EmailEnum.Result;
import com.beanframework.email.domain.EmailEnum.Status;

public class EmailDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5428432457219895180L;
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
	public static final String ATTACHMENTS = "attachments";

	private String name;

	private String toRecipients;

	private String ccRecipients;

	private String bccRecipients;

	private String subject;

	private String text;

	private String html;

	private Status status;

	private Result result;

	private String message;

	private File[] attachments;

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

	public File[] getAttachments() {
		return attachments;
	}

	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}

}
