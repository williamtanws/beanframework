package com.beanframework.history.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.history.HistoryConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = HistoryConstants.Table.HISTORY)
public class History extends AbstractDomain {

	private static final long serialVersionUID = 5992760081038782486L;
	public static final String MODEL = "History";

	private String ip;

	private String userAgent;

	private String module;

	private String action;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
