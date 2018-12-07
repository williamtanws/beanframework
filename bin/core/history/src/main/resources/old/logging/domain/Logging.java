package com.beanframework.logging.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.base.AbstractDomain;
import com.beanframework.logging.LoggingConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = LoggingConstants.TABLE_LOG)
public class Logging extends AbstractDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3387103539059426180L;
	public static final String MODEL = "Logging";
	public static final String IP = "ip";
	public static final String CHANNEL = "channel";
	public static final String OPERATE = "operate";
	public static final String RESULT = "result";

	private String ip;

	private String channel;

	private String operate;

	private String result;

	@Lob
	@Column(length = 100000)
	private String exception;

	@Transient
	private Date createdDateFrom;

	@Transient
	private Date createdDateTo;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getCreatedDateFrom() {
		return createdDateFrom;
	}

	public void setCreatedDateFrom(Date createdDateFrom) {
		this.createdDateFrom = createdDateFrom;
	}

	public Date getCreatedDateTo() {
		return createdDateTo;
	}

	public void setCreatedDateTo(Date createdDateTo) {
		this.createdDateTo = createdDateTo;
	}

}
