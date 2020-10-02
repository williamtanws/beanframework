package com.beanframework.imex.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.ImexType;
import com.beanframework.media.domain.Media;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = ImexConstants.Table.IMEX)
public class Imex extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TYPE = "type";
	public static final String DIRECTORY = "directory";
	public static final String FILENAME = "fileName";
	public static final String QUERY = "query";
	public static final String HEADER = "header";
	public static final String SEPERATOR = "seperator";
	public static final String MEDIAS = "medias";

	@Audited(withModifiedFlag = true)
	@Enumerated(EnumType.STRING)
	private ImexType type;

	@Audited(withModifiedFlag = true)
	private String directory;

	@Audited(withModifiedFlag = true)
	private String fileName;

	@Audited(withModifiedFlag = true)
	@Lob
	@Column(length = 10000)
	private String query;

	@Audited(withModifiedFlag = true)
	@Lob
	@Column(length = 10000)
	private String header;

	@Audited(withModifiedFlag = true)
	private String seperator;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@AuditJoinTable(inverseJoinColumns = @JoinColumn(name = "media_uuid"))
	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = ImexConstants.Table.IMEX_MEDIA_REL, joinColumns = @JoinColumn(name = "imex_uuid", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "media_uuid", referencedColumnName = "uuid"))
	private List<Media> medias = new ArrayList<Media>();

	public ImexType getType() {
		return type;
	}

	public void setType(ImexType type) {
		this.type = type;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public List<Media> getMedias() {
		return medias;
	}

	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}

}
