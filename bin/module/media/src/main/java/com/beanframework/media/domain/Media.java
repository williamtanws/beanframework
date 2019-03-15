package com.beanframework.media.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.media.MediaConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = MediaConstants.Table.MEDIA)
public class Media extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8147085933412547043L;
	public static final String FILENAME = "fileName";
	public static final String FILETYPE = "fileType";
	public static final String FILESIZE = "fileSize";
	public static final String TITLE = "title";
	public static final String CAPTION = "caption";
	public static final String ALTTEXT = "altText";
	public static final String DESCRIPTION = "description";
	public static final String URL = "url";
	public static final String LOCATION = "location";

	@Audited(withModifiedFlag = true)
	private String fileName;

	@Audited(withModifiedFlag = true)
	private String fileType;

	@Audited(withModifiedFlag = true)
	private Long fileSize;

	@Audited(withModifiedFlag = true)
	private String title;

	@Audited(withModifiedFlag = true)
	private String caption;

	@Audited(withModifiedFlag = true)
	private String altText;

	@Audited(withModifiedFlag = true)
	private String description;

	@Audited(withModifiedFlag = true)
	private String url;

	@Audited(withModifiedFlag = true)
	private String location;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
