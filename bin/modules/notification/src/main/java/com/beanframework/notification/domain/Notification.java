package com.beanframework.notification.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.notification.NotificationConstants;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
@Table(name = NotificationConstants.Table.NOTIFICATION)
public class Notification extends GenericEntity {

	private static final long serialVersionUID = 5992760081038782486L;
	public static final String ICON = "icon";
	public static final String MESSAGE = "message";
	public static final String TYPE = "type";

	private String icon;
	private String message;
	private String type;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@CollectionTable(name = NotificationConstants.Table.NOTIFICATION_PARAMETER, joinColumns = @JoinColumn(name = "notification_uuid"))
	Map<String, String> parameters = new HashMap<String, String>();

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
