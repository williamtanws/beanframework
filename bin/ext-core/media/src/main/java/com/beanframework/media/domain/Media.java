package com.beanframework.media.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.media.MediaConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = MediaConstants.Table.MEDIA)
public class Media extends AbstractDomain {

	private static final long serialVersionUID = 5992760081038782486L;
	public static final String MODEL = "Media";

	private String name;

	@Enumerated(EnumType.STRING)
	private MimeType mimeType;

	private Long size;

	private String location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MimeType getMimeType() {
		return mimeType;
	}

	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
